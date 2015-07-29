organization := "test"
name := "docker"
version := "0.1.0"
scalaVersion := "2.11.5"

enablePlugins(DockerPlugin)

mainClass in (Compile, run) := Some("com.apiumtech.br.gateways.Orchestrator")

docker <<= (docker dependsOn assembly)

dockerfile in docker := {
	val jarFile = (outputPath in assembly).value
	val appDirPath = "/app"
	val jarTargetPath = s"$appDirPath/${jarFile.name}"

	new Dockerfile {
		from("java")
		add(jarFile, jarTargetPath)
		workDir(appDirPath)
		entryPoint("java", "-jar", jarTargetPath, "com.apiumtech.br.gateways.Orchestrator")
	}
}

buildOptions in docker := BuildOptions(cache = false)