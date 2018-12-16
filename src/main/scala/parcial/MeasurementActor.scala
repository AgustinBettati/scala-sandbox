package parcial

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * @author Agustin Bettati
  * @version 1.0
  */
case class Measurement(name: String, value: Int)
case class Stats(name: String, min: Int, max: Int, total: Int, count: Int)
case class NotFound(name: String)
case class GetStats(name: String)
case class Reset(name: String)

class Statistics extends Actor {

  private var map: Map[String, List[Int]] = _

  override def receive: Receive = {
    case Measurement(name, value) =>
      map.get(name) match {
        case Some(list) =>
          map += (name -> (value :: list))
        case None =>
          map += name -> List(value)
      }

    case GetStats(name) =>
      map.get(name) match {
        case Some(list) =>
          sender() ! Stats(name, list.min, list.max, list.sum, list.length)
        case None =>
          sender() ! NotFound(name)
      }

    case Reset(name) =>
      map -= name
  }
}

class MyActor extends Actor {

  var map : Map[String, List[Int]] = Map()

  override def receive: Receive = {

    case Measurement(name, value) =>
      map.get(name) match {
        case Some(pijas) =>
          map += (name -> (value::pijas))
        case None =>
          map += (name -> List(value))
      }

  }
}

class StatisticsPrinter extends Actor {

  override def receive: Receive = {
    case Stats(name, min, max, sum, length) =>
      println(s"$name: $min, $max, $sum, $length")
    case NotFound(name) =>
      println(s"$name is not present")
  }
}

object StatisticsTester extends App {

  private val mySystem = ActorSystem("mySystem")
  private val statictics: ActorRef = mySystem.actorOf(Props[Statistics])
  private val printer: ActorRef = mySystem.actorOf(Props[StatisticsPrinter])

  statictics ! Measurement("techo", 10)
  statictics ! Measurement("techo", 15)
  statictics ! Measurement("techo", 9)
  statictics ! Measurement("techo", 20)

  statictics.tell(GetStats("techo"), printer)
}
