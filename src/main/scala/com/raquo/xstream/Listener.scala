package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

// @TODO[performance] replace Options with js.UndefOr?

// @TODO[API] change this to object method somehow? Should this be a value class?

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
    maybeNext: Option[T => Unit],
    maybeError: Option[E => Unit],
    maybeComplete: Option[() => Unit]
  ): Listener[T, E] = new Listener[T, E] {
    override val next: js.Function1[T, Unit] = js.Any.fromFunction1(maybeNext.getOrElse(noop1[T]))
    override val error: js.Function1[E, Unit] = js.Any.fromFunction1(maybeError.getOrElse(noop1[E]))
    override val complete: js.Function0[Unit] = js.Any.fromFunction0(maybeComplete.getOrElse(noop0))
  }

  def create[T, E](onNext: T => Unit): Listener[T, E] = new Listener[T, E] {
    override val next: js.Function1[T, Unit] = onNext
    override val error: js.Function1[E, Unit] = noop1 _
    override val complete: js.Function0[Unit] = js.Any.fromFunction0(noop0)
  }

  // TODO: This should have error type
  def create[T, E](onNext: T => Unit, onError: Any => Unit): Listener[T, E] = new Listener[T, E] {
    override val next: js.Function1[T, Unit] = onNext
    override val error: js.Function1[E, Unit] = onError
    override val complete: js.Function0[Unit] = js.Any.fromFunction0(noop0)
  }

  def create[T, E](onNext: T => Unit, onComplete: () => Unit): Listener[T, E] = new Listener[T, E] {
    override val next: js.Function1[T, Unit] = onNext
    override val error: js.Function1[E, Unit] = noop1 _
    override val complete: js.Function0[Unit] = onComplete
  }

  def create[T, E](onNext: T => Unit, onError: E => Unit, onComplete: () => Unit): Listener[T, E] = new Listener[T, E] {
    override val next: js.Function1[T, Unit] = onNext
    override val error: js.Function1[E, Unit] = onError
    override val complete: js.Function0[Unit] = onComplete
  }
}
