import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging}

/**
  * Created by glucero on 12/6/16.
  */
class ActorLogic extends Actor with ActorLogging{
  override def receive: Receive = {
    case t:Data => log.info("message receive in state {} {}",t,sender)
    case _ => sender() ! Close
  }
}
