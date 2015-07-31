FROM java
MAINTAINER apiumtest

EXPOSE 1338 1339

ADD target/. /app/

WORKDIR /app

ENTRYPOINT ["java", "-cp", "gateways-1.0-SNAPSHOT.jar", "com.apiumtech.br.gateways.Orchestrator"]
