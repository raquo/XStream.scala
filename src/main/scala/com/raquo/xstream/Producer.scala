package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
trait Producer[T] extends js.Object {

  // @TODO What's the meaning of this?

  def start(listener: Listener[T]): Unit

  def stop(): Unit
}

object Producer {

  def apply[T](
    onStart: Listener[T] => Unit,
    onStop: () => Unit
  ): Producer[T] = new Producer[T] {
    override def start(listener: Listener[T]): Unit = onStart(listener)
    override def stop(): Unit = onStop()
  }
}
