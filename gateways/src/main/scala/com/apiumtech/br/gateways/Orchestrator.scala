package com.apiumtech.br.gateways

import akka.actor.{ActorSystem, Props}
import com.apiumtech.br.gateways.http.HttpConsumer
import com.apiumtech.br.gateways.ping.PingGateway
import com.apiumtech.br.gateways.user.UserGateway

/**
 * @author kevin 
 * @since 7/17/15.
 */
object Orchestrator {
  def main(args: Array[String]) {
    val system = ActorSystem("business-router")
    Seq (
      Props(classOf[HttpConsumer], Seq(UserGateway(), PingGateway()), "netty-http:http://0.0.0.0:8080?matchOnUriPrefix=true")
    ).foreach(prop => system.actorOf(prop))
  }
}
