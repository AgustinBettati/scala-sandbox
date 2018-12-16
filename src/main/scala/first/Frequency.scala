package first

import scala.io.Source

/**
  * @author Agustin Bettati
  * @version 1.0
  */

case class Vote(province: String, district: String, candidate: String, votes: Int)

object Frequency extends App{

  val lines: List[String] = Source.fromFile("src/main/scala/parcial/votos/votos.txt").getLines().toList

  private val strings: Seq[String] = lines.map(_.toLowerCase)


  private val words: Seq[String] = strings.flatMap(_.split("[^a-z]").toList).filter(_.nonEmpty)

  val freq: Map[String, Int] = words.groupBy(e => e).map {
        case (word, list) => word -> list.length
      }

  val top50: Seq[(String, Int)] = freq.toList.sortBy(-_._2).take(50)

  top50.foreach(println)
}
