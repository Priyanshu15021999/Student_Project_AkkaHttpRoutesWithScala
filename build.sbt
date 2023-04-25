ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "ScalaWithMongoDBRoutesProject"
  )
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % "test",
  "org.reactivemongo" %% "reactivemongo" % "1.0.7",
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.9.0",
  "de.flapdoodle.embed" % "de.flapdoodle.embed.mongo" % "4.5.1" % Test,
 // "org.reactivemongo" %% "reactivemongo-bson-macros" % "1.0.7",
  "org.reactivemongo" %% "reactivemongo" % "0.20.13" % "provided",
  "org.reactivemongo" %% "reactivemongo" % "1.0"
)
val AkkaVersion = "2.7.0"
val AkkaHttpVersion = "10.5.0"


