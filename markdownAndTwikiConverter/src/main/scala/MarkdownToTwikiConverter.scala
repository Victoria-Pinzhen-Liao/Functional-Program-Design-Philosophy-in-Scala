object MarkdownToTwikiConverter extends App {
  // Sample Markdown string, including headings, separators, tables, code blocks, ordered lists, and hyperlinks
  val markdownText: String =
    """
      | # Main Title
      | ## Subtitle
      | ### Third Level Title
      | #### Fourth Level Title
      | ##### Fifth Level Title
      | ------
      | **Bold** *Italic*
      | Regular text
      | Above the separator
      | ----
      | Below the separator
      | Table example:
      | First Header  | Second Header
      | ------------- | -------------
      | Content Cell  | Content Cell
      | Content Cell  | Content Cell
      |
      | ```scala
      | println("Hello, world!")
      | ```
      |
      | 1. First item
      | 2. Second item
      | 3. Third item
      | - First item
      | - Second item
      | - Third item
      |
      | Visit our [website](http://example.com).
      |""".stripMargin

  // Conversion function
  def convertMarkdownToTwiki(markdown: String): String = {
    var inCodeBlock = false // Flag to indicate if we are inside a code block

    val convertedLines = markdown.split("\n").flatMap { line =>
      val trimmedLine = line.trim // Trim leading and trailing spaces
      if (trimmedLine.startsWith("```")) {
        inCodeBlock = !inCodeBlock // Toggle state
        if (inCodeBlock) Some("<verbatim>") else Some("</verbatim>")
      } else if (inCodeBlock) {
        Some(line) // Keep original line, including spaces
      } else {
        trimmedLine match {
          // Convert headings
          case l if l.startsWith("# ") => Some(s"---+ ${l.drop(2)}")
          case l if l.startsWith("## ") => Some(s"---++ ${l.drop(3)}")
          case l if l.startsWith("### ") => Some(s"---+++ ${l.drop(4)}")
          case l if l.startsWith("#### ") => Some(s"---++++ ${l.drop(5)}")
          case l if l.startsWith("##### ") => Some(s"---+++++ ${l.drop(6)}")
          // Convert separators
          case l if l.startsWith("----") => Some("---")
          // Convert unordered list items
          case l if l.startsWith("- ") => Some(s"   * ${l.drop(2)}")
          // Convert bold and italic text
          case l => Some(l.replaceAll("\\*\\*(.*?)\\*\\*", "==$1==").replaceAll("\\*(.*?)\\*", "_$1_"))
        }
      }
    }.map {
      // Process hyperlinks
      case l if l.contains("[") && l.contains("]") && l.contains("(") && l.contains(")") =>
        l.replaceAll("\\[(.*?)\\]\\((.*?)\\)", "[[$2][$1]]")
      case other => other
    }

    convertedLines.mkString("\n")
  }


  // Use the conversion function
  val twikiText = convertMarkdownToTwiki(markdownText)
  println(twikiText)
}

/*
Output:

  ---+ Main Title
    ---++ Subtitle
    ---+++ Third Level Title
    ---++++ Fourth Level Title
    ---+++++ Fifth Level Title
    ---
    == Bold == _Italic_
    Regular text
    Above the separator
    ---
    Below the separator
Table example:
  First Header | Second Header
    ---
    Content Cell | Content Cell
Content Cell | Content Cell

<verbatim>
  println("Hello, world!")
</verbatim>

1. First item
2. Second item
3. Third item
  * First item
  * Second item
  * Third item

Visit our[[http: //example.com][website]].

Process finished
with exit code 0

*/