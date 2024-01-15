object View extends  App {
  import scala.collection.View

  def time[R](block: => R): Long = {
    val start = System.nanoTime()
    block
    System.nanoTime() - start
  }

  val numbers = (1 to 1000000).toList

  // With view
  val timeWithView = time {
    val view = numbers.view.map(_ * 2).filter(_ > 5)
//    println(s"view $view")
    val resultWithView = view.take(10).toList
  }

  // Without view
  val timeWithoutView = time {
    val processedNumbers = numbers.map(_ * 2).filter(_ > 5)
//    println(s"processedNumbers $processedNumbers")
    val resultWithoutView = processedNumbers.take(10)
  }

  println(s"Time with View: $timeWithView nanoseconds")
  println(s"Time without View: $timeWithoutView nanoseconds")

//  Time with View: 6729800 nanoseconds
//  Time without View : 85503600 nanoseconds
}
