scalaVersion := "2.13.13"

organization := "dive"
name := "poolack"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.2.10",
  "com.typesafe.akka" %% "akka-stream" % "2.6.21",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.10"
)

libraryDependencies += "org.reactivemongo" %% "reactivemongo" % "1.0.10"


libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test



libraryDependencies += "org.slf4j" % "slf4j-simple" % "2.0.12"
