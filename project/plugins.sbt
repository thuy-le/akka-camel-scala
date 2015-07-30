logLevel := Level.Warn
addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.2.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.3")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")
addSbtPlugin("org.scala-sbt.plugins" % "sbt-onejar" % "0.8")
externalResolvers <<= resolvers map { rs =>
    Resolver.withDefaultResolvers(rs, mavenCentral = false)
}