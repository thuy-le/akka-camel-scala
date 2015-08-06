FROM maven
MAINTAINER apiumtest

EXPOSE 1339

ADD . /usr/share/winbits

WORKDIR /usr/share/winbits

USER root

export MAVEN_OPTS='-Xmx512m -XX:MaxPermSize=128m'

RUN mvn clean

RUN mvn install

ENTRYPOINT ["java", "-cp", "target/gateways-1.0-SNAPSHOT.jar", "com.apiumtech.br.gateways.Orchestrator"]
