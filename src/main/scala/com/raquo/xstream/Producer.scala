package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
trait Producer[T, EE <: Exception] extends js.Object {

  // @TODO What's the meaning of this?

  def start(listener: Listener[T, EE]): Unit

  def stop(): Unit
}

object Producer {

  def apply[T, EE <: Exception](
    onStart: Listener[T, EE] => Unit,
    onStop: () => Unit
  ): Producer[T, EE] = new Producer[T, EE] {
    override def start(listener: Listener[T, EE]): Unit = onStart(listener)
    override def stop(): Unit = onStop()
  }
}
