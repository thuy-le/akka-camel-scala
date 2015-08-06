FROM java
MAINTAINER apiumtest

EXPOSE 1339

ADD target/. /usr/share/winbits

WORKDIR /usr/share/winbits

USER root

RUN ls

ENTRYPOINT ["java", "-cp", "*.jar", "com.apiumtech.br.gateways.Orchestrator"]
