name := "dailyProgrammerProject"

version := "1.0"

scalaVersion := "2.11.1"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq( "com.typesafe.play" % "play-json_2.11" % "2.6.3",
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scala-lang" % "scala-compiler" % "2.11.8"
)