class Outer {
  class Inner
}

object AdvancedType extends App {


  def printInner(inner: Outer#Inner): Unit = {
    println(inner)
  }


}
