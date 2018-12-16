package parcial.votos

import scala.io.Source

/**
  * @author Agustin Bettati
  * @version 1.0
  */
case class Vote(province: String, district: String, candidate: String, votes: Int)

object Vote {
  def totalVotes(votes: List[Vote]): Int = {
    votes.map(_.votes).sum
  }

  def obtainWinner(votes: List[Vote]): String = {
    votes.groupBy(_.candidate).maxBy { case (candidate, personalVotes) => personalVotes.map(_.votes).sum }._1
  }

  def bestDistrictOf(votes: List[Vote], candidate: String): String = {
    votes.filter(_.candidate == candidate).groupBy(_.district)
      .maxBy { case (province, specificVotes) => specificVotes.map(_.votes).sum }._1
  }

  def obtainVotesFromFile(filePath: String): List[Vote] = {
    val lines: List[String] = Source.fromFile(filePath).getLines().toList
    lines.map(line => {
      val Array(prov, dictrict, candidate, votes) = line.split(',').map(_.trim)
      Vote(prov, dictrict, candidate, votes.toInt)
    })
  }
}

object Main extends App {

  val votes = Vote.obtainVotesFromFile("src/main/scala/parcial/votos/votos.txt")
  println(Vote.totalVotes(votes))
  println(Vote.obtainWinner(votes))
  println(Vote.bestDistrictOf(votes, "C"))
}
