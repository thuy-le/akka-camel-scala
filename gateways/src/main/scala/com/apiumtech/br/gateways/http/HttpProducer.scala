package com.apiumtech.br.gateways.http

import akka.actor.Status.Failure
import akka.actor.{ActorRef, Actor}
import akka.camel.{Producer, CamelMessage}
import org.apache.camel.Exchange

/**
 * Created by kate on 24/07/15.
 */

class HttpProducer(transformer: ActorRef) extends Actor with Producer {
  // bridgeEndpoint=true makes the producer ignore the Exchange.HTTP_URI header,
  // and use the endpoint's URI for request
  def endpointUri = "jetty:http://localhost:1338/?bridgeEndpoint=true"

  // before producing messages to endpoints, producer actors can pre-process
  // them by overriding the transformOutgoingMessage method
  //  override def transformOutgoingMessage(msg: Any) = msg match {
  //    case camelMsg: CamelMessage =>
  //      camelMsg.copy(headers =
  //      camelMsg.headers(Set(Exchange.HTTP_PATH, Exchange.HTTP_URI)))
  //  }

  // instead of replying to the initial sender(), producer actors can implement custom
  // response processing by overriding the routeResponse method
  //  override def routeResponse(msg: Any) { transformer forward msg }
}

class HttpTransformer extends Actor {
  def receive = {
    case msg: CamelMessage =>
      sender() ! (msg.mapBody { body: Array[Byte] =>
        new String(body).replaceAll("Akka ", "AKKA ")
      })
    case msg: Failure => sender() ! msg
  }
}