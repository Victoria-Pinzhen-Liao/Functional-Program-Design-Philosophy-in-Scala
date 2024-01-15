object mapExample extends App{

  val numbers = List(1, 2, 3, 4, 5)
  val descriptions = List("one", "two", "three", "four", "five")

  // First, combine numbers and descriptions together
  val zipped = numbers zip descriptions

  // Then filter out elements where the number is greater than 2
  val filtered = zipped.filter { case (number, _) => number > 2 }

  // Finally, map over the filtered list to generate the final strings
  val resultWithMap = filtered.map { case (number, description) => s"$number is $description" }

  // Result: List("3 is three", "4 is four", "5 is five")`
  resultWithMap.foreach(println)

}
