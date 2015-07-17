package com.apiumtech.br.domains

import akka.actor.{ActorRef, Actor}

/**
 * @author kevin 
 * @since 7/17/15.
 */

sealed trait SafeReceive { this: Actor =>
  final def receive = new PartialFunction[Any, Unit]() {
    def isDefinedAt(m: Any) = safeReceive(null).isDefinedAt(m)
    def apply(m: Any) = safeReceive(sender)(m)
  }

  def safeReceive: ActorRef => PartialFunction[Any, Unit]
}

trait SafeActor extends Actor with SafeReceive
