FROM java
MAINTAINER apiumtest

EXPOSE 1339

ADD target/. /usr/share/winbits

WORKDIR /usr/share/winbits

USER root

#RUN export MAVEN_OPTS='-Xmx512m -XX:MaxPermSize=128m'

#RUN mvn clean

#RUN mvn install

ENTRYPOINT ["java", "-cp", "*.jar", "com.apiumtech.br.gateways.Orchestrator"]
