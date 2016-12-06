import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

/**
  * Created by glucero on 12/6/16.
  */
class OrderTesting extends TestKit(ActorSystem("test-system"))
  with MustMatchers with WordSpecLike with BeforeAndAfterAll with ImplicitSender {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }


  "Order actor" must {

    "new Order " in {
      val actorRef = system.actorOf(Props[Order])

      actorRef ! "initaa"
      actorRef ! Create
//      actorRef ! Create
      Thread.sleep(10000)
    }
  }

}
