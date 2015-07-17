package test.apiumtech.br.domains.user

import com.apiumtech.br.domains.user.User
import org.scalatest.{Matchers, FlatSpec}

/**
 * @author kevin 
 * @since 7/16/15.
 */
case class UserSpecification() extends FlatSpec with Matchers {
  def user = User("name")

  "A User" should "be unregistered by default" in {
    user.isRegistered should equal(false)
  }

  it should "contain the registered date when registered" in {
    user.registered.registrationDate should not equal null
  }

  it should "be registered when registered" in {
    user.registered.isRegistered should equal(true)
  }
}
