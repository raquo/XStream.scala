package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
trait Producer[T, E] extends js.Object {

  def start: js.Function1[Listener[T, E], Unit]

  def stop: js.Function0[Unit]
}

object Producer {

  def apply[T, E](
    onStart: Listener[T, E] => Unit,
    onStop: () => Unit
  ): Producer[T, E] = new Producer[T, E] {
    override def start: js.Function1[Listener[T, E], Unit] = onStart
    override def stop: js.Function0[Unit] = onStop
  }
}
