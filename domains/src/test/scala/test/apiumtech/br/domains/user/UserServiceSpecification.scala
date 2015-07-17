package test.apiumtech.br.domains.user

import com.apiumtech.br.domains.user.UserService
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * @author kevin 
 * @since 7/16/15.
 */
case class UserServiceSpecification() extends FlatSpec with Matchers {
  def service = UserService()

  "A UserService" should "find a user by name" in {
    Await.result(service.user("name"), 500 milliseconds).name should equal ("name")
  }
}
