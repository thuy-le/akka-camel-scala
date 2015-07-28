organization := "com.apiumtech"
name := "winbits-proxy-mobile"
version := "0.1.0"
scalaVersion := "2.11.5"
maintainer := "apiumtest"
dockerExposedPorts in Docker := Seq(1339)
dockerEntrypoint in Docker := Seq("sh", "-c", "CLUSTER_IP=`/sbin/ifconfig eth0 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1 }'` bin/clustering $*")
dockerRepository := Some("apiumtest")
dockerBaseImage := "java"
enablePlugins(JavaAppPackaging)