package first

/**
  * @author Agustin Bettati
  * @version 1.0
  */
class PointComoJava(_x: Int, _y: Int) {

  val x: Int = _x
  val y: Int = _y

  println("Created point " + x + ", " + y)

  def add(that: PointComoJava): PointComoJava = new PointComoJava(x + that.x, y + that.y)

  override def toString: String = "Point(" + x +", "+ y+ ")"
}

object PointComoJava {
  def apply(x: Int, y: Int): PointComoJava = new PointComoJava(x, y)
}

case class Point(x: Int, y: Int){

//  sobrecarga de operadores
  def +(other: Point): Point = {
    Point(x + other.x, y + other.y)
  }
}

object Point{
  def apply: Point = Point(0, 0)
}
//implemento hash code, equals, to string, apply, unapply (para pattern matching)


object PointApp extends App {
  private val pointchoto = new PointComoJava(2,3)
  private val pointchoto2 = new PointComoJava(4,5)
  println(pointchoto == pointchoto2)

  val a = Point(4,5)
  val b = Point
  private val result: String = a match {
    case Point(0, 0) => "centro del eje"
    case Point(_, 0) => "sobre eje x"
    case Point(0, _) => "sobre eje y"
    case Point(x, y) => s"Punto en $x, $y"
  }
  print(result)

}

