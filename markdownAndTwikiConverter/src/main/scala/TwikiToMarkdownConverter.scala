object TwikiToMarkdownConverter extends App {
  // Sample TWiki text string
  val twikiText: String =
    """
      |---+ Main Title
      |---++ Subtitle
      |---+++ Third Level Title
      |---++++ Fourth Level Title
      |---+++++ Fifth Level Title
      |---
      |__Bold__ _Italic_
      |Regular text
      |Above the separator
      |---
      |Below the separator
      |   * First unordered item
      |   * Second unordered item
      |   * Third unordered item
      |
      |<verbatim>
      |println("Hello, world!")
      |</verbatim>
      |
      |1 First ordered item
      |1 Second ordered item
      |1 Third ordered item
      |
      |[[http://example.com][Visit our website]].
      |""".stripMargin

  // Conversion function
  def convertTwikiToMarkdown(twiki: String): String = {
    var inCodeBlock = false // Flag to indicate if we are inside a code block

    val convertedLines = twiki.split("\n").map { line =>
      if (line.startsWith("<verbatim>")) {
        inCodeBlock = true
        "```"
      } else if (line.startsWith("</verbatim>")) {
        inCodeBlock = false
        "```"
      } else if (inCodeBlock) {
        line
      } else {
        line match {
          // Convert headings
          case l if l.startsWith("---+ ") => "# " + l.substring(5)
          case l if l.startsWith("---++ ") => "## " + l.substring(6)
          case l if l.startsWith("---+++ ") => "### " + l.substring(7)
          case l if l.startsWith("---++++ ") => "#### " + l.substring(8)
          case l if l.startsWith("---+++++ ") => "##### " + l.substring(9)
          // Convert separators
          case l if l.startsWith("---") => "------"
          // Convert unordered list items
          case l if l.trim.startsWith("*") => "- " + l.trim.substring(1).trim
          // Convert ordered list items
          case l if l.matches("""\d+ .*""") => "1. " + l.substring(l.indexOf(' ') + 1)
          // Convert bold and italic text
          case l => l.replace("__", "**").replace("_", "*")
        }
      }
    }.map {
      // Process hyperlinks
      case l if l.contains("[[") && l.contains("]]") =>
        l.replaceAll("\\[\\[(.*?)\\]\\[(.*?)\\]\\]", "[$2]($1)")
      case other => other
    }

    convertedLines.mkString("\n")
  }

  // Use the conversion function
  val markdownText = convertTwikiToMarkdown(twikiText)
  println(markdownText)
}
