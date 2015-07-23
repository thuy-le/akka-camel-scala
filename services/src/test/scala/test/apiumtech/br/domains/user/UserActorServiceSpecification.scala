package test.apiumtech.br.domains.user

import java.util.Date

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.apiumtech.br.domains.user.{User, UserActorService}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

/**
 * @author kevin 
 * @since 7/17/15.
 */

case class UserActorServiceSpecification() extends TestKit(ActorSystem("UserActorService")) with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfterAll {
  import UserActorService._

  override def afterAll = TestKit.shutdownActorSystem(system)

  def user = User("Some", new Date)
  def actor = system.actorOf(UserActorService.props())

  "A UserActorService" must {
    "send the user that is found " in {
      actor ! GetUser("Some")
      expectMsgType[UserFound]
    }

    "send an exception when the user is not found" in {
      actor ! GetUser("")
      expectMsgType[akka.actor.Status.Failure] // so the supervisor acts
    }
  }
}
