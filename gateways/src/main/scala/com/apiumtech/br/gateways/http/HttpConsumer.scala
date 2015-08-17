package com.apiumtech.br.gateways.http

import akka.actor.{ActorRef, ActorContext, ActorLogging}
import akka.camel.{CamelMessage, Consumer}
import com.apiumtech.br.domains.SafeActor
import com.apiumtech.br.domains.user.UserRepository

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
 * @author kevin 
 * @since 7/17/15.
 */

trait HttpRouter extends HttpSupport {
  implicit def futureStringToSomeFutureString(a: Future[String]): Option[Future[String]] = Some(a)

  implicit val timeout = akka.util.Timeout(250 milliseconds)

  def process(context: ActorContext, message: CamelMessage): Option[Future[String]]
}

case class HttpConsumer(producer: ActorRef, routes: Seq[HttpRouter], endpointUri: String) extends SafeActor with Consumer with ActorLogging with HttpSupport {
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
          case _ => sender ! http(404)
        }
        case _ => producer forward msg
      }
  }
}
