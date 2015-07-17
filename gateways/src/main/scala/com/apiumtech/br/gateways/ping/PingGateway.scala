package com.apiumtech.br.gateways.ping

import java.util.Date

import akka.actor.ActorContext
import akka.camel.CamelMessage
import com.apiumtech.br.gateways.http.HttpRouter

import scala.concurrent.Future

/**
 * @author kevin 
 * @since 7/17/15.
 */
case class PingGateway() extends HttpRouter {
  import scala.concurrent.ExecutionContext.Implicits.global
  val Ping = "/ping".r

  def process(context: ActorContext, msg: CamelMessage) = {
    request(msg) match {
      case ("GET", Ping()) =>
        for (
          date <- Future { new Date() }
        ) yield json(date)
      case _ => None
    }
  }
}
