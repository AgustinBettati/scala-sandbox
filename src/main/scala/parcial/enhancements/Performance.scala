package parcial.enhancements

/**
  * @author Agustin Bettati
  * @version 1.0
  */
object Perf {

  def perf(name: String)(op:  => Unit): Unit = {
    val start = System.currentTimeMillis()
    op
    val totalTime = System.currentTimeMillis - start
    println(s"Block $name lasted $totalTime miliseconds")
  }
}

object SmartLoggerTest extends App {

  import Perf._

  perf("calculations") {
    for(i <- 10 to 100000)
      Math.sqrt(i)
  }
}
