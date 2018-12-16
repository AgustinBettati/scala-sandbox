package parcial

case class Sales(date: String, amount: List[Double]){

}

object Sales {

  def salesOverAmount(sales: List[Sales], value: Int): List[Sales] =
    sales.filter(sale => sale.amount.sum > value)

  def daysWhereSalesDoubled(sales: List[Sales]): List[Sales] =
    sales.zip(sales.tail).filter { case (prevDay, cur) => cur.amount.sum > prevDay.amount.sum * 2 } map (_._2)

  def consecutivesDaysWithMostSales(sales: List[Sales], amt: Int): List[Sales] = {
   sales.sliding(amt).maxBy(list => list.map(_.amount.sum).sum)
}

}

object SalesTest extends App {

  val all = List(
    Sales("2015-05-05", List(1200, 233)),
    Sales("2015-05-06", List(3400, 24, 43)),
    Sales("2015-05-07", List(3434, 434, 543)),
    Sales("2015-05-08", List(30, 4, 3)),
    Sales("2015-05-09", List(440, 24, 3343)),
    Sales("2015-05-10", List(1, 24, 43)),
    Sales("2015-05-11", List(1)),
  )

//  println(Sales.salesOverAmount(all, 1000))
//  println(Sales.daysWhereSalesDoubled(all))
  println(Sales.consecutivesDaysWithMostSales(all, 2))

}
