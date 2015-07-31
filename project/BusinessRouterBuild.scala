import sbt._
import Keys._

object BusinessRouterBuild extends Build {

  lazy val root = (project in file(".") ).aggregate(gateways, domains, services).settings(
      run := {
        (run in gateways in Compile).evaluated
      },
      mainClass := {
        (mainClass in gateways in Compile).value
      }
    )

  lazy val domains = project.settings(libraryDependencies ++= Defaults)
  lazy val services = project.settings(libraryDependencies ++= Camel ++ Akka ++ Defaults).dependsOn(domains)
  lazy val gateways = project.settings(libraryDependencies ++= Camel ++ Defaults).dependsOn(services)

  Keys.`package` := {
    (Keys.`package` in (gateways, Compile)).value
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