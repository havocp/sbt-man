package com.typesafe.sbt

import sbt._

object ManPlugin extends sbt.Plugin {
  object ManKeys {
    val man = taskKey[Unit]("Display documentation for a setting on the terminal")
    val manText = taskKey[String]("Obtain the text of the documentation for a setting")
    val manHtml = taskKey[String]("Obtain HTML documentation for a setting")
  }

  override val settings: Seq[Setting[_]] = Nil
}
