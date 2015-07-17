package com.apiumtech.br.domains.user

import com.apiumtech.br.domains.user.UserRepository.UserNotFoundException

import scala.concurrent.Future

/**
 * @author kevin 
 * @since 7/16/15.
 */
trait UserRepository {
  def findByName(name: String): Future[User]
}

case object MockUserRepository extends UserRepository {
  import scala.concurrent.ExecutionContext.Implicits.global

  def findByName(name: String) = Future { if (name == "" || name == "NF") throw new UserNotFoundException(name) else User(name).registered }
}

object UserRepository {
  case class UserNotFoundException(userName: String) extends Exception(s"User $userName does not exists")

  def mock = MockUserRepository
}
