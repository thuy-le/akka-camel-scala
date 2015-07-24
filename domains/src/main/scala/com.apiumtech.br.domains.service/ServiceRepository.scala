package com.apiumtech.br.domains.service

import com.apiumtech.br.domains.service.ServiceRepository.ServiceNotFoundException

import scala.concurrent.Future

/**
 * @author kate
 * @since 7/24/15.
 */
trait ServiceRepository {
  def findByName(name: String): Future[Seq[String]]
}

case object MockServiceRepository extends ServiceRepository {
  import scala.concurrent.ExecutionContext.Implicits.global

  def findByName(name: String) = Future {
    if (name == "" || name == "NF")
    throw new ServiceNotFoundException(name) else Seq("custome", name)
  }
}


object ServiceRepository {
  case class ServiceNotFoundException(service: String) extends Exception(s"Service $service does not contain any data")
  def mock = MockServiceRepository
}
