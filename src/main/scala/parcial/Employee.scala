package parcial

/**
  * @author Agustin Bettati
  * @version 1.0
  */
case class Employee(name: String, sinceYears: Int, dependents: List[Employee])

object Employee {

  def obtainEmployees(president: Employee): List[Employee] ={
     president :: president.dependents.flatMap(obtainEmployees)
  }

  def amountOfEmployee(president: Employee): Int = {
     1 + president.dependents.map(dep => amountOfEmployee(dep)).sum
  }

  def peopleWithDependents(president: Employee): List[Employee] = {
    obtainEmployees(president) filter (_.dependents.nonEmpty)
  }


  def bossesThatWaitMoreThan3(president: Employee): List[Employee] = {
    peopleWithDependents(president) filter
      (_.dependents.combinations(2).exists{case List(e1,e2) => Math.abs(e1.sinceYears - e2.sinceYears) > 3})
  }
}

object EmployeeTest extends App {
  val carlos = Employee("Carlos", 1, Nil)
  val apu = Employee("Agustin", 1, Nil)
  val jorge = Employee("Jorge", 5, List(carlos))
  val president = Employee("jefe", 10, List(apu, jorge))

  println(Employee.bossesThatWaitMoreThan3(president))
}
