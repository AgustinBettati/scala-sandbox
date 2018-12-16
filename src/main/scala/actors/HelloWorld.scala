package actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * @author Agustin Bettati
  * @version 1.0
  */
class HelloWorld extends Actor {

  override def receive: Receive = {
    case msg =>
      println(msg)

  }
}

object HelloTest extends App {
  val system = ActorSystem("mySystem")
  val hello: ActorRef = system.actorOf(Props[HelloWorld], "hello")
  hello ! "Hello"
}