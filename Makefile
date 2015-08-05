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

docker-build:
	docker build -t $(IMAGE) .

docker-run:
	docker run --rm -it -p 1339:1339 $(IMAGE)

run:
	java -cp target/*.jar com.apiumtech.br.gateways.Orchestrator

dockerize:
	mvn clean
	mvn install
	docker build -t $(IMAGE) .
	docker run --rm -it -p 1339:1339 $(IMAGE)

push-image:
	docker push $(IMAGE)