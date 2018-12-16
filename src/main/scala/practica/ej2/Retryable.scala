package practica.ej2

/**
  * @author Agustin Bettati
  * @version 1.0
  */

object Retry {

  private var condition = true

  def retryable(op: => Unit): Unit = {
    condition = true
    while (condition)
      op
  }

  def retry(continue: Boolean): Unit = if (!continue) condition = false
}


object Test extends App {

  import Retry._

  var i = 0
  retryable {
    i += 1
    println("i=" + i)
    retry(i < 10)
  }

}
