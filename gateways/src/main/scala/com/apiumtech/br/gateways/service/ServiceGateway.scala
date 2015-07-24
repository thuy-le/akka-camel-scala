package com.apiumtech.br.gateways.service

import akka.actor.ActorContext
import akka.camel.CamelMessage
import akka.pattern.ask
import com.apiumtech.br.domains.service.{ServiceActorService}
import com.apiumtech.br.domains.service.ServiceActorService.ServiceFound
import com.apiumtech.br.gateways.http.HttpRouter

import scala.concurrent.Future

/**
 * @author kevin 
 * @since 7/17/15.
 */
case class ServiceGateway() extends HttpRouter {
  import scala.concurrent.ExecutionContext.Implicits.global
  def actorService(context: ActorContext) = context.actorOf(ServiceActorService.props())

  val ServiceByName = "/service/(.+)".r

  def process(context: ActorContext, msg: CamelMessage) = {
    request(msg) match {
      case ("GET", ServiceByName(name)) =>
        println("im called");
        for (
          serviceFound <- actorService(context).ask(ServiceActorService.GetService(name)).asInstanceOf[Future[ServiceFound]]
        ) yield json(serviceFound.service)
      case _ => None
    }
  }
}
