package actors.crawler

import java.io.IOException

import actors.{Count, Counter, GetStats, Printer}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool

import scala.io.Source

/**
  * @author Agustin Bettati
  * @version 1.0
  */
case class FetchURL(url: String)
case class FetchedURL(url: String, content: String)

class Fetcher extends Actor {
  //se reparte entre 4 actores distintos
  val workers: ActorRef = context.actorOf(RoundRobinPool(4).props(Props[FetchWorker]))

  override def receive: Receive = {
    case msg: FetchURL =>
      //cuando el fetchWorker responde va directo al sender de aca
      workers.forward(msg)
  }
}

class FetchWorker extends Actor {
  override def receive: Receive = {
    case FetchURL(url) =>
      try {
        val content = Source.fromURL(url).getLines().mkString("\n")
        sender() ! FetchedURL(url, content)
      }
      catch{
        case _: IOException =>
      }
  }
}


class FetcherPrinter extends Actor {
  override def receive: Receive = {
    case FetchedURL(url, content) => println(s"Fetched URL: $url, ${content.take(20)} ...")
  }
}

object Fetcher extends App {
  val system = ActorSystem("mySystem")
  private val fetcher: ActorRef = system.actorOf(Props[Fetcher])
  private val printer: ActorRef = system.actorOf(Props[FetcherPrinter])

  fetcher.tell(FetchURL("https://scala-lang.org"), printer)
  fetcher.tell(FetchURL("https://scala-lang.org"), printer)
}



