package com.apiumtech.br.gateways

import akka.actor.{ActorSystem, Props}
import com.apiumtech.br.gateways.http.{HttpConsumer, HttpProducer}
import com.apiumtech.br.gateways.ping.PingGateway
import com.apiumtech.br.gateways.user.UserGateway
import com.apiumtech.br.gateways.service.ServiceGateway

/**
 * @author kevin 
 * @since 7/17/15.
 */
object Orchestrator {
  def main(args: Array[String]) {
    val system = ActorSystem("winbits-router")

    val httpProducer = system.actorOf(Props(classOf[HttpProducer]))

    Seq (
      Props(classOf[HttpConsumer], httpProducer, Seq(UserGateway(), ServiceGateway(), PingGateway()), "netty-http:http://0.0.0.0:1339?matchOnUriPrefix=true")
    ).foreach(prop => system.actorOf(prop))

  }
}
