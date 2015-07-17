package com.apiumtech.br.gateways.user

import akka.actor.ActorContext
import akka.camel.CamelMessage
import akka.pattern.ask
import com.apiumtech.br.domains.user.{UserDTO, UserActorService}
import com.apiumtech.br.domains.user.UserActorService.UserFound
import com.apiumtech.br.gateways.http.HttpRouter

import scala.concurrent.Future

/**
 * @author kevin 
 * @since 7/17/15.
 */
case class UserGateway() extends HttpRouter {
  import scala.concurrent.ExecutionContext.Implicits.global
  def actorService(context: ActorContext) = context.actorOf(UserActorService.props())

  val UserByName = "/user/(.+)".r

  def process(context: ActorContext, msg: CamelMessage) = {
    request(msg) match {
      case ("GET", UserByName(name)) =>
        for (
          userFound <- actorService(context).ask(UserActorService.GetUser(name)).asInstanceOf[Future[UserFound]]
        ) yield json[UserDTO](userFound.user)
      case _ => None
    }
  }
}
