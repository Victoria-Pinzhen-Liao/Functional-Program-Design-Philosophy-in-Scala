object LazyListvsRange extends App {
  def isPrime(n: Int): Boolean = {
    if (n <= 1) return false
    if (n <= 3) return true
    if (n % 2 == 0 || n % 3 == 0) return false
    var i = 5
    while (i * i <= n) {
      if (n % i == 0 || n % (i + 2) == 0) return false
      i += 6
    }
    true
  }

  val startTimeLazyList = System.nanoTime()
  val resultLazyList = LazyList.from(1).filter(isPrime)(1)
  val endTimeLazyList = System.nanoTime()
  val durationLazyList = endTimeLazyList - startTimeLazyList

  println(s"Result with LazyList: $resultLazyList, Time taken: $durationLazyList nanoseconds") // 831855000 nanoseconds

  val startTimeRange = System.nanoTime()
  val resultRange = (1 to 10000000).filter(isPrime)(1)
  val endTimeRange = System.nanoTime()
  val durationRange = endTimeRange - startTimeRange

  println(s"Result with Range: $resultRange, Time taken: $durationRange nanoseconds")

  // Result with LazyList: 3, Time taken: 29,216,200 nanoseconds
  // Result with Range: 3, Time taken: 868,594,100 nanoseconds

}
