import sbt._
import Keys._
import sbtassembly.AssemblyOption
import sbtassembly.AssemblyPlugin.autoImport._

object BusinessRouterBuild extends Build {

  lazy val root = (project in file(".")).aggregate(gateways, domains, services).settings(
    run := {
      (run in gateways in Compile).evaluated
    },
    mainClass := {
      (mainClass in gateways in Compile).value
    },
    assembly := {
      (assembly in gateways in Compile).value
    },
    assemblyOutputPath in assembly := {
      (target in gateways in assembly).value / (assemblyJarName in gateways in assembly).value
    }
  )

  lazy val domains = project.settings(libraryDependencies ++= Defaults)
  lazy val services = project.settings(libraryDependencies ++= Camel ++ Akka ++ Defaults).dependsOn(domains)
  lazy val gateways = project.settings(commonSettings: _*)
    .settings(libraryDependencies ++= Camel ++ Defaults).dependsOn(services)

  /** **************************************************************************************/
  lazy val akkaVersion = "2.3.12"
  lazy val camelVersion = "2.15.2"
  lazy val slf4jVersion = "1.7.10"
  lazy val jacksonVersion = "2.2.2"

  lazy val scope = "compile"

  lazy val Defaults = Logging ++ Testing

  lazy val commonSettings = Seq(
    version := "0.1-SNAPSHOT",
    organization := "com.apiumtech",
    scalaVersion := "2.10.4"
  )

  lazy val Akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion % scope,
    "com.typesafe.akka" %% "akka-camel" % akkaVersion % scope,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % scope,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion % scope,
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion % scope
  )

  lazy val Logging = Seq(
    "org.slf4j" % "slf4j-simple" % slf4jVersion % scope
  )

  lazy val Testing = Seq(
    "org.scalatest" %% "scalatest" % "2.0" % "test"
  )

  lazy val Camel = Seq(

    "org.apache.camel" % "camel-core" % camelVersion % scope,
    "org.apache.camel" % "camel-scala" % camelVersion % scope,
    "org.apache.camel" % "camel-ahc" % camelVersion % scope,
    "org.apache.camel" % "camel-netty-http" % camelVersion % scope,
    ("org.apache.camel" % "camel-jms" % camelVersion % scope)
      .exclude("org.springframework", "spring-jms")
      .exclude("org.springframework", "spring-aop")
      .exclude("org.springframework", "spring-context")
      .exclude("org.springframework", "spring-beans"),

    ("org.apache.camel" % "camel-testng" % camelVersion % scope)
      .exclude("org.springframework", "spring-test")
      .exclude("org.springframework", "spring-aop")
      .exclude("org.springframework", "spring-context")
      .exclude("org.springframework", "spring-beans")

  )
}