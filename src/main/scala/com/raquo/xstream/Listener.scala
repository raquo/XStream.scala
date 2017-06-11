package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.|

// @TODO[API] Should js.Error be part of the API, or should we wrap it into JavascriptException?

@ScalaJSDefined
trait Listener[-T] extends js.Object {

  def next(value: T): Unit

  def error(error: Exception | js.Error): Unit

  def complete(): Unit
}

object Listener {

  // @TODO[Elegance] Is there a better way to define empty no-op functions?

  private[xstream] def noop0(): Unit = ()

  private[xstream] def noop1[T](x: T): Unit = ()

  def apply[T](
    onNext: T => Unit = noop1[T] _,
    onError: Exception | js.Error => Unit = noop1[Exception | js.Error] _,
    onComplete: () => Unit = noop0
  ): Listener[T] = new Listener[T] {
    override def next(value: T): Unit = onNext(value)
    override def error(error: Exception | js.Error): Unit = onError(error)
    override def complete(): Unit = onComplete()
  }
}
