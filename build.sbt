sbtPlugin := true

name := "sbt-man"

organization := "com.typesafe.sbt"

version := "0.1.0"

scalacOptions := Seq("-unchecked", "-deprecation")

description := "sbt plugin to give man pages to sbt settings"

publishMavenStyle := false

publishTo <<= (version) { version: String =>
   val scalasbt = "http://scalasbt.artifactoryonline.com/scalasbt/"
   val (name, u) = if (version.contains("-SNAPSHOT")) ("sbt-plugin-snapshots", scalasbt+"sbt-plugin-snapshots")
                   else ("sbt-plugin-releases", scalasbt+"sbt-plugin-releases")
   Some(Resolver.url(name, url(u))(Resolver.ivyStylePatterns))
}

credentials += Credentials(Path.userHome / ".ivy2" / ".sbt-credentials")

