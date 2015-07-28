FROM java
MAINTAINER apiumtest
EXPOSE 1339
ADD . /opt/docker
WORKDIR /opt/docker

RUN echo "deb http://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list
RUN apt-get update && apt-get install sbt -y --force-yes

USER root

# Validation
CMD ["sbt", "test"]

# Run
CMD ["sbt", "project gateways", "run"]