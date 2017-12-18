package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

/** @see https://github.com/staltz/xstream#producer */
@ScalaJSDefined
trait Producer[+T] extends js.Object {

  // These methods are protected because they should not be called from Scala code, but you might need to override them

  protected def start(listener: Listener[T]): Unit

  protected def stop(): Unit
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
