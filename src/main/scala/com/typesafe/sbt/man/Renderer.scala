package com.typesafe.sbt.man

class HtmlRenderer {

  // this instance is NOT thread-safe
  private val pegdown = new org.pegdown.PegDownProcessor()

  def render(markdown: String): String = synchronized {
    Option(pegdown.markdownToHtml(markdown)).getOrElse {
      throw new Exception("Failed to render markdown")
    }
  }
}

class TextRenderer {
  // we might get a little fancier here later
  def render(markdown: String): String =
    markdown
}
