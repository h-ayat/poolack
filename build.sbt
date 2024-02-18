scalaVersion := "2.13.11"

organization := "dive"
name := "poolack"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.2.10",
  "com.typesafe.akka" %% "akka-stream" % "2.6.21",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.10"
)
