package com.typesafe.sbt

import sbt._
import complete.DefaultParsers._
import complete.Parser
import Def.Initialize

object ManPlugin extends sbt.Plugin {
  object ManKeys {
    val man = inputKey[Unit]("Display documentation for a setting on the terminal")
    val manText = inputKey[Option[String]]("Obtain the text of the documentation for a setting")
    val manHtml = inputKey[Option[String]]("Obtain HTML documentation for a setting")
  }

  private val finder = new man.ManPageFinder
  private lazy val htmlRenderer = new man.HtmlRenderer
  private lazy val textRenderer = new man.TextRenderer

  private def getText(state: State, log: Logger, key: String): Option[String] = {
    finder.findMarkdown(key, log) flatMap { markdown =>
      Some(textRenderer.render(markdown))
    }
  }

  private def getHtml(state: State, log: Logger, key: String): Option[String] = {
    finder.findMarkdown(key, log) flatMap { markdown =>
      Some(htmlRenderer.render(markdown))
    }
  }

  private val manTextParser: Def.Initialize[State => Parser[Option[String]]] =
    Def.setting {
      (state: State) =>
        Inspect.allKeyParser(state) map { key =>
          // TODO how do we really get logging in here
          getText(state, state.globalLogging.full, key.label)
        }
    }

  private val manHtmlParser: Def.Initialize[State => Parser[Option[String]]] =
    Def.setting {
      (state: State) =>
        Inspect.allKeyParser(state) map { key =>
          // TODO how do we really get logging in here
          getHtml(state, state.globalLogging.full, key.label)
        }
    }

  override val settings: Seq[Setting[_]] = Seq(
    ManKeys.man := {
      val text = ManKeys.manText.evaluated getOrElse {
        "No documentation available for that setting"
      }
      val streams = Keys.streams.value
      streams.log.info(text)
    },
    ManKeys.manText := {
      manTextParser.parsed
    },
    ManKeys.manHtml := {
      manHtmlParser.parsed
    })
}
