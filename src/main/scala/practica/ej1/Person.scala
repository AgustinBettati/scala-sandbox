package practica.ej1

/**
  * @author Agustin Bettati
  * @version 1.0
  */
case class Person(name: String, age: Int, children: List[Person] = Nil){
  def descendants: List[Person] =  this :: children.flatMap(_.descendants)

  def youngerAndOlder: (List[Person], List[Person]) = {
    this.descendants.partition(_.age < age)
  }

  def grandchildren: List[Person] = {
    children.flatMap(_.children)
  }

  def twinSons: List[(Person, Person)]= {
    children.tails.toList.tail.flatMap(list => list.zip(children)).filter {case (a,b) => a.age == b.age}
//        brothers.combinations(2).map{case Seq(a,b) => (a,b)}.toList.filter {case (a,b) => a.age == b.age}
  }

  def brothersWith4orMoreYearDif: List[(Person, Person)]= {
    children.tails.toList.tail.flatMap(list => list.zip(children)).filter { case (a,b) => Math.abs(a.age - b.age) >4 }
  }
}

object Person {

  def getPeopleWithNoChildren(people: List[Person]): List[Person] = {
    people.filter(_.children.isEmpty)
  }

  def getPeopleWithSonsOfAverage4(people: List[Person]): List[Person] = {

    people.filter(person => (person.children.map(_.age).sum / person.children.length) == 4 )
  }

  def peopleWithFatherOlderThan(people: List[Person], value: Int): List[Person] = {
    people.flatMap(person => (if(person.age > value) person.children else Nil) ++ peopleWithFatherOlderThan(person.children, value))
  }

}


object Main extends App {
  private val value: PartialFunction[Any, Int] = {
    case n: Int => n + 1
    case s: String => s.length
  }
  val edad = 5
  val descripcion: String =
    if(edad > 20){
      "viejo"
    }
    else{
     "joven"
    }

  value
  val apu = Person("Agustin", 3)
  val john = Person("John", 4)
  val andi = Person("Andi", 5)
  val children = List(apu, john, andi)
  val jorge = Person("Jorge", 20, children)
}
