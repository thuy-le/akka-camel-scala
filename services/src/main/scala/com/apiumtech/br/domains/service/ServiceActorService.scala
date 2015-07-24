package com.apiumtech.br.domains.service

import akka.actor.Props
import com.apiumtech.br.domains.SafeActor

/**
 * @author kevin 
 * @since 7/17/15.
 */

case class ServiceActorService(serviceService: ServiceService) extends SafeActor {
  import ServiceActorService._
  import akka.actor.Status.Failure

  import scala.concurrent.ExecutionContext.Implicits.global

  def safeReceive = sender => {
    case GetService(name) => serviceService.service(name).map(dto => sender ! ServiceFound(dto)).onFailure { case e => sender ! Failure(e) ; throw e }
  }
}

object ServiceActorService {
  case class GetService(service: String)
  case class ServiceFound(service: Seq[String])

  def props(serviceService: ServiceService = ServiceService()) = Props(ServiceActorService(serviceService))
}