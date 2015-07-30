import sbt._
import Keys._
import com.github.retronym.SbtOneJar

import sbtassembly.AssemblyPlugin._ 

object BusinessRouterBuild extends Build {

  // val commonSettings = Seq(
  //    scalaVersion := "2.11.5",
  //    version := "0.1",
  //    crossPaths := false
  // )

  // lazy val root = Project(
  //   id = "docker",
  //   base = file("."),
  //   settings = commonSettings ++
  //     Seq(
  //       run := {
  //         (run in gateways in Compile).evaluated
  //       },
  //       mainClass := {
  //         (mainClass in gateways in Compile).value
  //       }
  //     )
  // ) dependsOn(domains, services, gateways)

  // lazy val domains = Project(
  //   id = "domains",
  //   base = file("domains")
  // ) settings(libraryDependencies ++= Defaults)

  // lazy val services = Project(
  //   id = "services",
  //   base = file("services")
  // ) settings(libraryDependencies ++= Camel ++ Akka ++ Defaults) dependsOn domains

  // lazy val gateways = Project(
  //   id = "gateways",
  //   base = file("gateways")
  // ) settings(libraryDependencies ++= Camel ++ Defaults) dependsOn services

  lazy val root = (project in file(".") ).aggregate(gateways, domains, services).settings(
      run := {
        (run in gateways in Compile).evaluated
      },
      mainClass := {
        (mainClass in gateways in Compile).value
      },
      packageBin := {
        (packageBin in gateways in Compile).value
      }
    )

  lazy val domains = project.settings(assemblySettings).settings(libraryDependencies ++= Defaults)
  lazy val services = project.settings(assemblySettings).settings(libraryDependencies ++= Camel ++ Akka ++ Defaults).dependsOn(domains)
  lazy val gateways = Project(
    id = "gateways",
    base = file(".")
    ).settings(assemblySettings).settings(libraryDependencies ++= Camel ++ Defaults).dependsOn(services)

  Keys.`package` := {
    (Keys.`package` in (gateways, Compile)).value
    (Keys.`package` in (root, Compile)).value
  }

  /****************************************************************************************/
  lazy val akkaVersion = "2.3.12"
  lazy val camelVersion = "2.15.2"
  lazy val slf4jVersion = "1.7.10"
  lazy val jacksonVersion = "2.2.2"

  lazy val Defaults = Logging ++ Testing

  lazy val Akka = Seq (
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-camel" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion
  )

  lazy val Logging = Seq (
    "org.slf4j" % "slf4j-simple" % slf4jVersion
  )

  lazy val Testing = Seq (
    "org.scalatest" %% "scalatest" % "2.0" % "test"
  )

  lazy val Camel = Seq (
    "org.apache.camel" % "camel-core" % camelVersion,
    "org.apache.camel" % "camel-scala" % camelVersion,
    "org.apache.camel" % "camel-ahc" % camelVersion,
    "org.apache.camel" % "camel-jms" % camelVersion,
    "org.apache.camel" % "camel-netty-http" % camelVersion,
    "org.apache.camel" % "camel-jetty" % camelVersion,
    "org.apache.camel" % "camel-testng" % camelVersion
  )
}