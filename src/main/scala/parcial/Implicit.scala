package parcial

/**
  * @author Agustin Bettati
  * @version 1.0
  */
case class Point(x: Int, y: Int) {

  def *(scalar: Int): Point = {
    Point(x*scalar, y*scalar)
  }
}

object Point {
  implicit def convertFromTupleToPoint(tuple: (Int,Int)): Point = {
    Point(tuple._1, tuple._2)
  }

}


object Main extends App {

  import Point._


  private val point: Point = (2,3) * 5

  println(point)

}
