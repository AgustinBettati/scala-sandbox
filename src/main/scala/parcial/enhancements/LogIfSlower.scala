package parcial.enhancements

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent
import ExecutionContext.Implicits.global

/**
  * @author Agustin Bettati
  * @version 1.0
  */
object SmartLogger {

  def logIfSlow(op: => Unit): Unit = {
    val futureOp = Future { op }
    Thread.sleep(2000)
    if (!futureOp.isCompleted) {
      println("[warning] operation was too slow")
    }
  }
}

object Test extends App {

  import SmartLogger._

  logIfSlow {
    Thread.sleep(3000)
    println("pasaron 3s")
  }
  println("termino el log")

  Thread.sleep(20000)
}














