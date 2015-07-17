package com.apiumtech.br.domains.user

import scala.concurrent.Future

/**
 * @author kevin 
 * @since 7/16/15.
 */
trait UserService {
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit def toDTO(user: Future[User]): Future[UserDTO] = user.map(_.toDTO)

  def user(name: String): Future[UserDTO]
}

case class DefaultUserService(repository: UserRepository) extends UserService {
  def user(name: String) = repository.findByName(name)
}

object UserService {
  def apply(repository: UserRepository = UserRepository.mock): DefaultUserService = DefaultUserService(repository)
}