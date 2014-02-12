package com.typesafe.sbt.man

import sbt.IO

class ManPageFinder {
  def findMarkdown(key: String, log: sbt.Logger): Option[String] = {
    val streamOption = try {
      val filename = "/sbt/man/" + key + ".md"
      Option(this.getClass.getResourceAsStream(filename))
    } catch {
      case e: Exception => {
        log.warn(s"Exception loading resource: ${e.getClass.getName}: ${e.getMessage}")
        None
      }
    }

    try {
      for (stream <- streamOption) yield {
        try {
          IO.readStream(stream)
        } finally {
          stream.close()
        }
      }
    } catch {
      case e: Exception =>
        log.warn(s"Exception reading markdown: ${e.getClass.getName}: ${e.getMessage}")
        None
    }
  }
}
