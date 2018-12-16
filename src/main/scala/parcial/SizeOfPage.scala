package parcial

import scala.concurrent.{Await, Future}
import scala.io.Source
import scala.concurrent.Await._

/**
  * @author Agustin Bettati
  * @version 1.0
  */
object Ej1 extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  private val result: Future[Boolean] = sameSize("http://jsoup.org", "http://www.scala-lang.org")

  def sameSize(firstURL: String, secondURL: String): Future[Boolean] = {
    val fstDownloaded: Future[String] = download(firstURL)
    val sndDownloaded: Future[String] = download(secondURL)

    val size1: Future[Int] = fstDownloaded.map( content => content.length)
    val size2: Future[Int] = sndDownloaded.map( content => content.length)

    for {
      length1 <- fstDownloaded
      length2 <- sndDownloaded
    } yield length1 == length2
  }

  def download(url: String):Future[String] =
    Future {Source.fromURL(url).getLines().mkString("\n")}
}
