.PHONY:

PROJECT=winbits-proxy-mobile
IMAGE=apiumtest/$(PROJECT)
TEMP_CONTAINER=$(PROJECT)-deleteme

clean-only:
	mvn clean

install-only:
	mvn install

run-only:
	java -cp target/*.jar com.apiumtech.br.gateways.Orchestrator

run:
	mvn clean
	mvn install
	java -cp target/*.jar com.apiumtech.br.gateways.Orchestrator

docker-build:
	docker build -t $(IMAGE) .

docker-run:
	docker run -d --name akka-server -p 1339:1339 --link node-server:node-server $(IMAGE)

dockerize:
	mvn clean
	mvn install
	docker build -t $(IMAGE) .
	docker run -d --name akka-server -p 1339:1339 --link node-server:node-server $(IMAGE)

push-image:
	docker push $(IMAGE)