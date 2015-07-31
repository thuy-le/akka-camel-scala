logLevel := Level.Warn
addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.2.0")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")
externalResolvers <<= resolvers map { rs =>
    Resolver.withDefaultResolvers(rs, mavenCentral = false)
}