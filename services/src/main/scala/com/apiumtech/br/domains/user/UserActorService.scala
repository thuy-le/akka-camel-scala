package com.apiumtech.br.domains.user

import akka.actor.Props
import com.apiumtech.br.domains.SafeActor

/**
 * @author kevin 
 * @since 7/17/15.
 */

case class UserActorService(userService: UserService) extends SafeActor {
  import UserActorService._
  import akka.actor.Status.Failure

  import scala.concurrent.ExecutionContext.Implicits.global

  def safeReceive = sender => {
    case GetUser(name) => userService.user(name).map(dto => sender ! UserFound(dto)).onFailure { case e => sender ! Failure(e) ; throw e }
  }
}

object UserActorService {
  case class GetUser(name: String)
  case class UserFound(user: UserDTO)

  def props(userService: UserService = UserService()) = Props(UserActorService(userService))
}