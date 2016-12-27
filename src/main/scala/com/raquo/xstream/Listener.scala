package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
trait Listener[-T, -E] extends js.Object {

  val next: js.Function1[T, Unit]

  val error: js.Function1[E, Unit]

  val complete: js.Function0[Unit]
}

object Listener {

  // @TODO[Elegance] Is there a better way to define empty no-op functions?

  private[xstream] def noop0(): Unit = ()

  private[xstream] def noop1[T](x: T): Unit = ()

  def apply[T, E](
    onNext: T => Unit = noop1[T] _,
    onError: E => Unit = noop1[E] _,
    onComplete: () => Unit = noop0
  ): Listener[T, E] = new Listener[T, E] {
    override val next: js.Function1[T, Unit] = onNext
    override val error: js.Function1[E, Unit] = onError
    override val complete: js.Function0[Unit] = onComplete
  }
}
