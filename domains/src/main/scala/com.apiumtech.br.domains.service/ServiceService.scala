package com.apiumtech.br.domains.service

import scala.concurrent.Future

/**
 * @author kate
 * @since 7/24/15.
 */
trait ServiceService {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit def toDTO(service: Future[Service]): Future[ServiceDTO] = service.map(_.toDTO)

  def service(name: String): Future[Seq[String]]
}

case class DefaultServiceService(repository: ServiceRepository) extends ServiceService {
  def service(name: String) = repository.findByName(name)
}

object ServiceService {
  def apply(repository: ServiceRepository = ServiceRepository.mock): DefaultServiceService = DefaultServiceService(repository)
}