package test.apiumtech.br.domains.user

import com.apiumtech.br.domains.user.UserRepository
import org.scalatest.{FlatSpec, Matchers}
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * @author kevin 
 * @since 7/16/15.
 */
trait UserRepositorySpecification extends FlatSpec with Matchers {
  def implementation: String
  def repository: UserRepository

  s"A $implementation" should "find a user with the name" in {
    Await.result(repository.findByName("name"), 500 milliseconds) should not equal null
  }

  it should "find the user with that name" in {
    Await.result(repository.findByName("name"), 500 milliseconds).toDTO.name should equal("name")
  }
}

case class MockUserRepositorySpecification() extends UserRepositorySpecification {
  def implementation = "MockUserRepository"
  def repository = UserRepository.mock
}