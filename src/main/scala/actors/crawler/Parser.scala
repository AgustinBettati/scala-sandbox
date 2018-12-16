package actors.crawler

import java.net.{MalformedURLException, URL}

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool

import scala.util.Try

/**
  * @author Agustin Bettati
  * @version 1.0
  */
case class Parse(url: String, content: String)
case class Parsed(url: String, page: WebPage)

case class WebPage(links: List[String], title: String)

class Parser extends Actor {
  val cores: Int = Runtime.getRuntime.availableProcessors()
  val workers: ActorRef = context.actorOf(RoundRobinPool(cores).props(Props[ParserWorker]))

  override def receive: Receive = {
    case msg: Parse =>
      //cuando el parseWorker responde va directo al sender de aca
      workers.forward(msg)
  }
}

class ParserWorker extends Actor {
  override def receive: Receive = {
    case Parse(url, html) =>
      try{
        import scala.collection.JavaConverters._
        val current = new URL(url)
        val doc = org.jsoup.Jsoup.parse(html)
        val links = doc.select("a[href]").asScala.map(_.attr("href")).toList
        val validUrls = links.flatMap(l => Try(new URL(current, l)).toOption )
        val filteredUrls = validUrls.map(_.toString.takeWhile(_ != '#')).distinct.sorted

        val page = WebPage(filteredUrls, doc.title())
        sender() ! Parsed(url, page)
      }
      catch{
        case _: MalformedURLException =>
      }
  }
}


class ParserPrinter extends Actor {
  override def receive: Receive = {
    case Parsed(url, page) => println(s"Parsed ${page.title} and found ${page.links}")
  }
}

object Parser extends App {
  val system = ActorSystem("mySystem")
  private val parser: ActorRef = system.actorOf(Props[Parser])
  private val printer: ActorRef = system.actorOf(Props[ParserPrinter])

}
