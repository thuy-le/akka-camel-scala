package com.apiumtech.br.gateways.http

import akka.actor.{ActorContext, ActorLogging}
import akka.camel.{CamelMessage, Consumer}
import com.apiumtech.br.domains.SafeActor
import com.apiumtech.br.domains.user.UserRepository
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.apache.camel.Exchange

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
 * @author kevin 
 * @since 7/17/15.
 */

sealed trait HttpSupport {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def json[T](a: T)(implicit manifest: Manifest[T]) = mapper.writerWithType(manifest.runtimeClass).writeValueAsString(a)
  def http(status: Int, body: String = "") = CamelMessage(body, Map(Exchange.HTTP_RESPONSE_CODE -> status))

  def method(msg: CamelMessage) = msg.headers(Exchange.HTTP_METHOD).toString.toUpperCase
  def uri(msg: CamelMessage) = msg.headers(Exchange.HTTP_URI)

  def request(msg: CamelMessage) = method(msg) -> uri(msg)
}

trait HttpRouter extends HttpSupport {
  implicit def futureStringToSomeFutureString(a: Future[String]): Option[Future[String]] = Some(a)

  implicit val timeout = akka.util.Timeout(250 milliseconds)

  def process(context: ActorContext, message: CamelMessage): Option[Future[String]]
}

case class HttpConsumer(routes: Seq[HttpRouter], endpointUri: String) extends SafeActor with Consumer with ActorLogging with HttpSupport {
  import scala.concurrent.ExecutionContext.Implicits.global

  def safeReceive = sender => {
    case msg: CamelMessage =>
      routes.toArray.map(_.process(context, msg)).filter(_.isDefined).map(_.get) match {
        case Array(value) => value.onComplete {
          case Success(response) => sender ! http(200, response)
          case Failure(ex) => ex match {
            case ex: UserRepository.UserNotFoundException => sender ! http(404, json(ex.getMessage))
            case ex: Throwable => sender ! http(500, json(ex.getMessage))
          }
          case _ => sender ! http(404);
        }
        case _ => sender ! http(404, json(s"Route ${method(msg)} ${uri(msg)} not found."))
      }
  }
}
