package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

/** @see https://github.com/staltz/xstream */
@js.native
trait EStream[+T, +EE <: Exception] extends js.Object {

  def addListener(listener: Listener[T, EE]): Unit = js.native

  def removeListener(listener: Listener[T, EE]): Unit = js.native

  def subscribe[T2 >: T, EE2 >: EE <: Exception](listener: Listener[T2, EE2]): Subscription[T2, EE2] = js.native

  @JSName("map")
  def jsMap[U](project: js.Function1[T, U]): EStream[U, EE] = js.native

  def mapTo[U](projectedValue: U): EStream[U, EE] = js.native

  @JSName("filter")
  def jsFilter(passes: js.Function1[T, Boolean]): EStream[T, EE] = js.native

  def take(amount: Int): EStream[T, EE] = js.native

  def drop(amount: Int): EStream[T, EE] = js.native

  def last(): EStream[T, EE] = js.native

  def startWith[U >: T](initial: U): MemoryStream[U, EE] = js.native

  def endWhen(other: EStream[_, _]): EStream[T, EE] = js.native

  @JSName("fold")
  def jsFold[R](accumulate: js.Function2[R, T, R], seed: R): MemoryStream[R, EE] = js.native

  // @TODO add withExpectedError[ErrorType] method

  @JSName("replaceError")
  def jsReplaceAllErrors[U >: T](
    replace: js.Function1[Exception | js.Error, XStream[U]]
  ): XStream[U] = js.native

  /** This is private because it works only on streams of streams. See [[MetaStream.flatten]] */
  @JSName("flatten")
  private[xstream] def jsFlatten[T2, EE2 <: Exception](): EStream[T2, EE2] = js.native

  @JSName("compose")
  def jsCompose[T2, EE2 <: Exception, ResultStream <: EStream[T2, EE2]](
    operator: js.Function1[EStream[T, EE], ResultStream]
  ): ResultStream = js.native

  def remember(): MemoryStream[T, EE] = js.native

  @JSName("debug")
  def jsDebugWithSpy(spy: js.Function1[T, Any]): EStream[T, EE] = js.native

  @JSName("debug")
  def debugWithLabel(label: String): EStream[T, EE] = js.native

  def debug(): EStream[T, EE] = js.native

  def setDebugListener(listener: Listener[T, EE]): Unit = js.native

  // @TODO[Integrity] Seems that this violates covariance. Do we eve need this? Maybe provide similar functionality via .compose?
  private def imitate[U >: T, EE2 >: EE <: Exception](target: EStream[U, EE2]): Unit = js.native

  // @TODO[Integrity] Seems that this violates covariance. However, this is only exposed on ShamefulStream which is not covariant. Is that ok?
  private[xstream] def shamefullySendNext[U >: T](value: U): Unit = js.native

  // @TODO[Integrity] Seems that this violates covariance. However, this is only exposed on ShamefulStream which is not covariant. Is that ok?
  private[xstream] def shamefullySendError[EE2 >: EE <: Exception](error: EE2): Unit = js.native

  private[xstream] def shamefullySendComplete(): Unit = js.native
}

object EStream extends TupleOps {

  /** See also [[XStream.throwUnexpectedError]] */
  @inline def throwExpectedError[E <: Exception](error: E): EStream[Nothing, E] = {
    RawXStream.`throw`(error)
  }

  // create

  @inline def create[T, EE <: Exception](): EStream[T, EE] = {
    RawXStream.create()
  }

  @inline def create[T, EE <: Exception](producer: Producer[T, EE]): EStream[T, EE] = {
    RawXStream.create(producer)
  }

  // from<X>

  @inline def fromPromise[T, EE <: Exception](promise: js.Promise[T]): EStream[T, EE] = {
    RawXStream.fromPromise(promise)
  }

  // merge

  @inline def merge[T, EE <: Exception](streams: EStream[T, EE]*): EStream[T, EE] = {
    RawXStream.merge(streams: _*)
  }

  // combine

  @inline def combine[T1, T2, EE <: Exception](
    stream1: EStream[T1, EE],
    stream2: EStream[T2, EE]
  ): EStream[(T1, T2), EE] = {
    RawXStream
      .combine(stream1, stream2)
      .jsMap(JSArrayToTuple2[T1, T2] _)
  }

  @inline def combine[T1, T2, T3, EE <: Exception](
    stream1: EStream[T1, EE],
    stream2: EStream[T2, EE],
    stream3: EStream[T3, EE]
  ): EStream[(T1, T2, T3), EE] = {
    RawXStream
      .combine(stream1, stream2, stream3)
      .jsMap(JSArrayToTuple3[T1, T2, T3] _)
  }

  @inline def combine[T1, T2, T3, T4, EE <: Exception](
    stream1: EStream[T1, EE],
    stream2: EStream[T2, EE],
    stream3: EStream[T3, EE],
    stream4: EStream[T4, EE]
  ): EStream[(T1, T2, T3, T4), EE] = {
    RawXStream
      .combine(stream1, stream2, stream3, stream4)
      .jsMap(JSArrayToTuple4[T1, T2, T3, T4] _)
  }
}
