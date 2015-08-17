/**
 * Created by kate on 17/08/2015.
 */

package com.apiumtech.br.gateways.http

import akka.camel.CamelMessage
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.apache.camel.Exchange

trait HttpSupport {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def json[T](a: T)(implicit manifest: Manifest[T]) = mapper.writerWithType(manifest.runtimeClass).writeValueAsString(a)
  def http(status: Int, body: String = "") = CamelMessage(body, Map(Exchange.HTTP_RESPONSE_CODE -> status))

  def method(msg: CamelMessage) = msg.headers(Exchange.HTTP_METHOD).toString.toUpperCase
  def uri(msg: CamelMessage) = msg.headers(Exchange.HTTP_URI)

  def responseCode(msg: CamelMessage) = msg.headers(Exchange.HTTP_RESPONSE_CODE)

  def request(msg: CamelMessage) = method(msg) -> uri(msg)

  def response(msg: CamelMessage) = responseCode(msg)

}
