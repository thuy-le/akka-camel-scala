package com.apiumtech.br.gateways.http

import akka.actor.Status.Failure
import akka.actor.{Actor}
import akka.camel.{Producer}


/**
 * Created by kate on 24/07/15.
 */

class HttpProducer() extends Actor with Producer with HttpSupport {
  	// bridgeEndpoint=true makes the producer ignore the Exchange.HTTP_URI header,
  	// and use the endpoint's URI for request
  	def endpointUri = "netty-http:http://node-server:1338/?bridgeEndpoint=true"

  	override def transformResponse(msg: Any) = {
      msg match {
        case f:Failure => {
          sender ! http(404, "{status: 'failure', statusCode: 404}")
        }
        case _ => {
          super.transformResponse(msg)
        }
      }
    }
}
