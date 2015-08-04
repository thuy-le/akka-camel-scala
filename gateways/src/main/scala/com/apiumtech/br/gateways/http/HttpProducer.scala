package com.apiumtech.br.gateways.http

import akka.actor.{Actor}
import akka.camel.{Producer}

/**
 * Created by kate on 24/07/15.
 */

class HttpProducer() extends Actor with Producer {
  // bridgeEndpoint=true makes the producer ignore the Exchange.HTTP_URI header,
  // and use the endpoint's URI for request
  def endpointUri = "netty-http:http://node-server:1338/?bridgeEndpoint=true"
}
