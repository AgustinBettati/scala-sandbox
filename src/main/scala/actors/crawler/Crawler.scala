package actors.crawler

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * @author Agustin Bettati
  * @version 1.0
  */
case class Crawl(url: String)

case class FoundPage(rootUrl: String, url: String, page: WebPage)

class Crawler extends Actor {
  val fetcher: ActorRef = context.actorOf(Props[Fetcher])
  val parser: ActorRef = context.actorOf(Props[Parser])
  var root: String = _
  var originalSender : ActorRef = _

  var pending: Set[String] = Set()
  var processed: Set[String] = Set()

  override def receive: Receive = {
    case Crawl(url) =>
      root = url
      originalSender = sender()
      fetcher ! FetchURL(url)

    case FetchedURL(url, content) =>
      parser ! Parse(url, content)

    case Parsed(url, webPage) =>
      originalSender ! FoundPage(root, url, webPage)
      pending =   webPage.links.toSet.take(5) ++ pending -- processed
      pending.takeRight(10).foreach { p =>
        processed += p
        fetcher ! FetchURL(p)
      }
  }
}

class CrawlerPrinter extends Actor {
  override def receive: Receive = {
    case FoundPage(rootUrl, url, page) => println(s"[New URL] $url")
  }
}

object CrawlerTest extends App {
  val system = ActorSystem("mySystem")
  private val crawler: ActorRef = system.actorOf(Props[Crawler])
  private val printer: ActorRef = system.actorOf(Props[CrawlerPrinter])

  crawler.tell(Crawl("https://scala-lang.org"), printer)

}
