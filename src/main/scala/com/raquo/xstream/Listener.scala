package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js.|

@ScalaJSDefined
trait Listener[-T, -EE <: Exception] extends js.Object {

  def next(value: T): Unit

  // @TODO[Integrity] Will Scala.js route into these two methods correctly?

  def error(error: EE): Unit

  def error(error: Exception | js.Error): Unit

  def complete(): Unit
}

object Listener {

  // @TODO[Elegance] Is there a better way to define empty no-op functions?

  private[xstream] def noop0(): Unit = ()

  private[xstream] def noop1[T](x: T): Unit = ()

  def apply[T, EE <: Exception](
    onNext: T => Unit = noop1[T] _,
    onExpectedError: EE => Unit = noop1[EE] _,
    onUnexpectedError: Exception | js.Error => Unit = noop1[Exception | js.Error] _,
    onComplete: () => Unit = noop0
  ): Listener[T, EE] = new Listener[T, EE] {
    override def next(value: T): Unit = onNext(value)
    override def error(error: EE): Unit = onExpectedError(error)
    override def error(error: Exception | js.Error): Unit = onUnexpectedError(error)
    override def complete(): Unit = onComplete()
  }
}
