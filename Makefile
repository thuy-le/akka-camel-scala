# Make file for winbits-proxy-mobile
echo "Building winbits-proxy-mobile application"
# Dockerizeing using sbt-docker plugin
sbt docker
# Running the application from docker
docker run --rm -it -p 1339:1339 testdocker