organization := "test"
name := "docker"
version := "0.1.0"
scalaVersion := "2.11.5"

exportJars := true

enablePlugins(DockerPlugin)

assemblyExcludedJars in assembly := { 
  val cp = (fullClasspath in assembly).value
  cp filter {_.data.getName == "compile-0.1.0.jar"}
}

docker <<= (docker dependsOn assembly)

dockerfile in docker := {
	val jarFile = (outputPath in assembly).value
	val appDirPath = "/app"
	val jarTargetPath = s"$appDirPath/${jarFile.name}"

	new Dockerfile {
		from("java")
		add(jarFile, jarTargetPath)
		workDir(appDirPath)
		entryPoint("java", "-cp", jarTargetPath, "com.apiumtech.br.gateways.Orchestrator")
	}
}

buildOptions in docker := BuildOptions(cache = false)

