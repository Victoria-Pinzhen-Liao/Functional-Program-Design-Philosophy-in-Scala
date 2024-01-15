object forExample extends App{
  val numbers = List(1, 2, 3, 4, 5)
  val descriptions = List("one", "two", "three", "four", "five")

  // Using a for expression to combine the two lists, and selecting only those combinations where the number is greater than 2
  val result = for {
    (number, description) <- numbers zip descriptions
    if number > 2
  } yield s"$number is $description"

  result.foreach(println)
  // Result: List("3 is three", "4 is four", "5 is five")
}
