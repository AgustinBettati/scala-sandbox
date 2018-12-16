package parcial

/**
  * @author Agustin Bettati
  * @version 1.0
  */
object Stringss {
  // Tiene que recibir un unico parametro para envolver y extender a la clase String en este caso
  implicit class Str(str: String) {
    def wow: String = str.toUpperCase + " !!!"
  }
}
object ImplicitClasses extends App {
  // los imports pueden estar en cualquier lado, en este caso tengo todo lo del object Strings
  import Stringss._
  val a: String = "hola"
  println(a.wow)
  // cuando el compilador ve que String no tiene el metodo wow(), busca si hay definida una clase implicita, el cual me extiende el comportamiento
}

