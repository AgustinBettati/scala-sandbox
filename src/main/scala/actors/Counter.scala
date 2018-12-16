package actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * @author Agustin Bettati
  * @version 1.0
  */
case class Count(value: Int)
case object GetStats
case class Stats(value: Int)

class Counter extends Actor {
  //hay mutabilidad, pero en actores nunca hay problema de concurrencia porque un actor maneja un mensaje a la vez
  var total: Int = 0

  override def receive: Receive = {
    case Count(value) =>
      total += value
    case GetStats =>
      sender() ! Stats(total)
  }
}

class Printer extends Actor {
  override def receive: Receive = {
    case msg => println(msg)
  }
}

object CounterTest extends App {
  val system = ActorSystem("mySystem")
  private val counter: ActorRef = system.actorOf(Props[Counter])
  private val printer: ActorRef = system.actorOf(Props[Printer])
  counter ! Count(10)
  counter ! Count(5)

  counter.tell(GetStats, printer)
}
