.PHONY:

PROJECT=winbits-proxy-mobile
IMAGE=apiumtest/$(PROJECT)
TEMP_CONTAINER=$(PROJECT)-deleteme


clean-only:
	mvn clean

install-only:
	mvn install

install:
	mvn clean
	mvn install

run:
	java -cp target/*.jar com.apiumtech.br.gateways.Orchestrator

docker-build:
	docker build -t $(IMAGE) .

docker-run:
	docker run -d --name akka-server -p 1339:1339 --link node-server:node-server $(IMAGE)

docker-run-dev:
	docker run --rm --name akka-server -it -p 1339:1339 --link node-server:node-server $(IMAGE)

docker-push:
	docker push $(IMAGE)

dockerize:
	docker-build docker-run

push-image:
	docker push $(IMAGE)