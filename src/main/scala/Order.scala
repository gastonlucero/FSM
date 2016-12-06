import akka.actor.{ActorRef, FSM}

/**
  * Created by glucero on 12/6/16.
  */


sealed trait State

case object OrderNew extends State

case object OrderCreated extends State

case object OrderAproved extends State

case object OrderPaused extends State

case object OrderExecuted extends State

case object OrdercCanceled extends State

case object OrderClosed extends State

case object OrderRejected extends State

case object OrderEnded extends State


sealed trait Data

case object Uninitialized extends Data

case object New extends Data

case object Create extends Data

case object Aprove extends Data

case object Execute extends Data

case object Pause extends Data

case object Cancel extends Data

case object Close extends Data

case object Reject extends Data


trait Message

case class StartMessage(ref: ActorRef) extends Message

class Order extends FSM[State, Data] {

  startWith(OrderNew, Uninitialized)

  when(OrderNew) {
    case Event(t: String, Uninitialized) => {

      val sta = if (t.equals("init")) Create else Reject
      println(" new -> create ", sta)
      goto(OrderCreated) using sta
    }
    case _ => stay()
  }

  when(OrderCreated) {
    case Event(Create, t) => {
      if (t == Reject) {
        println("rejected en created")
        goto(OrderRejected)
      } else {
        println(" create -> aproved ")
        goto(OrderAproved)
      }
    }
  }

  when(OrderAproved) {
    case Event(Aprove, _) => {
      println(" aprove -> end ")
      stop()
    }
  }


  when(OrderRejected) {
    case Event(Reject, _) => stay()
  }

  whenUnhandled {
    case Event(Create, _) => {
      println("unhnalded")
      goto(OrderCreated) using stateData
    }
    case _ => stay()
  }

  onTransition {
    case OrderNew -> OrderCreated => {
      log.info("creando orden {} {}", stateData, nextStateData)
    }
    case OrderCreated -> OrderAproved => log.info("aprobada  {} {}", stateData, nextStateData)
  }

  initialize()
}
