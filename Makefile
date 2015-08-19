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
	install
	java -cp target/*.jar com.apiumtech.br.gateways.Orchestrator

docker-build:
	mvn clean
	mvn install
	docker build -t $(IMAGE) .

docker-run:
	docker run -d --name akka-server -p 5000:1339 --link node-server:node-server $(IMAGE)

docker-rundev:
	docker run --rm --name akka-server -it -p 5000:1339 --link node-server:node-server $(IMAGE)

docker-push:
	docker push $(IMAGE)

push-image:
	docker push $(IMAGE)