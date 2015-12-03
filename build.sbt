organization := "pt.tecnico.sirs"
name := "Medical Record Database"
version := "0.0.1"

lazy val `mrd` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"
initialize := {
  val required = "1.8"
  val current  = sys.props("java.specification.version")
  assert(current == required, s"Unsupported JDK: java.specification.version $current != $required")
}
javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

libraryDependencies ++= Seq(
//  "ws.securesocial" %% "securesocial" % "master-SNAPSHOT",
  "com.github.stapl-dsl" % "stapl-core" % "acsac2015",
  //Logging
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0" % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.3" % "test",
  //Testing
  specs2 % "test"
)

resolvers += "jitpack" at "https://jitpack.io"

scalacOptions ++= Seq(
  "-deprecation",                   //Emit warning and location for usages of deprecated APIs.
  "-encoding", "UTF-8",             //Use UTF-8 encoding. Should be default.
  "-feature",                       //Emit warning and location for usages of features that should be imported explicitly.
  "-language:implicitConversions",  //Explicitly enables the implicit conversions feature
  "-unchecked",                     //Enable detailed unchecked (erasure) warnings
  "-Xfatal-warnings",               //Fail the compilation if there are any warnings.
  "-Xlint",                         //Enable recommended additional warnings.
  "-Yinline-warnings",              //Emit inlining warnings.
  "-Yno-adapted-args",              //Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  "-Ywarn-dead-code"                //Warn when dead code is identified.
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )
