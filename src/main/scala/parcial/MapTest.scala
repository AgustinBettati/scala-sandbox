package parcial

/**
  * @author Agustin Bettati
  * @version 1.0
  */
object  MapTest extends App {
  var map = Map("techo" -> List(5,3,4,5),
  "piso" -> List(4,3,3,2))


  map = map + ("techo" -> List(1000))

  println(map)
}
