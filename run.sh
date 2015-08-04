docker run -d --name node-server -p 172.17.42.1:1338:1338 apiumtest/winbits-backend-mobile
docker run -d --name akka-server -p 1339:1339 --link node-server:node-server apiumtest/winbits-proxy-mobile