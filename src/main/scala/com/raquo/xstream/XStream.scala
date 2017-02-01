package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

/** @see https://github.com/staltz/xstream */
@js.native
trait XStream[+T, +EE <: Exception] extends js.Object {

  def addListener(listener: Listener[T, EE]): Unit = js.native

  def removeListener(listener: Listener[T, EE]): Unit = js.native

  def subscribe[T2 >: T, EE2 >: EE <: Exception](listener: Listener[T2, EE2]): Subscription[T2, EE2] = js.native

  @JSName("map")
  def mapJs[U](project: js.Function1[T, U]): XStream[U, EE] = js.native

  def mapTo[U](projectedValue: U): XStream[U, EE] = js.native

  @JSName("filter")
  def filterJs(passes: js.Function1[T, Boolean]): XStream[T, EE] = js.native

  def take(amount: Int): XStream[T, EE] = js.native

  def drop(amount: Int): XStream[T, EE] = js.native

  def last(): XStream[T, EE] = js.native

  def startWith[U >: T](initial: U): MemoryStream[U, EE] = js.native

  def endWhen(other: XStream[_, _]): XStream[T, EE] = js.native

  @JSName("fold")
  def foldJs[R](accumulate: js.Function2[R, T, R], seed: R): MemoryStream[R, EE] = js.native

  // @TODO add withExpectedError[ErrorType] method

  // @TODO API
  @JSName("replaceError")
  def replaceAllErrorsJs[U >: T](
    replace: js.Function1[Exception | js.Error, XStream[U, Nothing]]
  ): XStream[U, Nothing] = js.native

  /** This is private because it works only on streams of streams. See [[MetaStream.flatten]] */
  @JSName("flatten")
  private[xstream] def flattenJs[T2, EE2 <: Exception](): XStream[T2, EE2] = js.native

  // @TODO[API] Rename *Js methods to js* for better auto-completion
  @JSName("compose")
  def composeJs[T2, EE2 <: Exception, ResultStream <: XStream[T2, EE2]](
    operator: js.Function1[XStream[T, EE], ResultStream]
  ): ResultStream = js.native

  def remember(): MemoryStream[T, EE] = js.native

  @JSName("debug")
  def debugJs(spy: js.Function1[T, Any]): XStream[T, EE] = js.native

  def debug(label: String): XStream[T, EE] = js.native

  def debug(): XStream[T, EE] = js.native

  def setDebugListener(listener: Listener[T, EE]): Unit = js.native

  // @TODO[Integrity] Seems that this violates covariance. Do we eve need this? Maybe provide similar functionality via .compose?
  private def imitate[U >: T, EE2 >: EE <: Exception](target: XStream[U, EE2]): Unit = js.native

  // @TODO[Integrity] Seems that this violates covariance. However, this is only exposed on ShamefulStream which is not covariant. Is that ok?
  private[xstream] def shamefullySendNext[U >: T](value: U): Unit = js.native

  // @TODO[Integrity] Seems that this violates covariance. However, this is only exposed on ShamefulStream which is not covariant. Is that ok?
  private[xstream] def shamefullySendError[EE2 >: EE <: Exception](error: EE2): Unit = js.native

  private[xstream] def shamefullySendComplete(): Unit = js.native
}

/** @see https://github.com/staltz/xstream */
object XStream {

  // Simple streams

  @inline def of[T](value: T): XStream[T, Nothing] =
    RawXStream.of(value)

  @inline def never(): XStream[Nothing, Nothing] =
    RawXStream.never()

  @inline def empty(): XStream[Nothing, Nothing] =
    RawXStream.empty()

  @inline def throwExpectedError[E <: Exception](error: E): XStream[Nothing, E] =
    RawXStream.`throw`(error)

  @inline def rethrowUnexpectedError(error: Exception | js.Error): XStream[Nothing, Nothing] =
    RawXStream.`throw`(error)

  @inline def periodic(period: Int): XStream[Int, Nothing] =
    RawXStream.periodic(period)

  // create & createWithMemory

  @inline def create[T, EE <: Exception](): XStream[T, EE] =
    RawXStream.create()

  @inline def create[T, EE <: Exception](producer: Producer[T, EE]): XStream[T, EE] =
    RawXStream.create(producer)

  @inline def createWithMemory[T, EE <: Exception](): MemoryStream[T, EE] =
    RawXStream.createWithMemory()

  @inline def createWithMemory[T, EE <: Exception](producer: Producer[T, EE]): MemoryStream[T, EE] =
    RawXStream.createWithMemory(producer)

  // from<X>

  @inline def fromSeq[T](seq: Seq[T]): XStream[T, Nothing] =
    RawXStream.fromArray(seq.toJSArray)

  @inline def fromPromise[T, EE <: Exception](promise: js.Promise[T]): XStream[T, EE] =
    RawXStream.fromPromise(promise)

  @inline def fromJSArray[T](array: js.Array[T]): XStream[T, Nothing] =
    RawXStream.fromArray(array)

  //  @inline def fromJsObservable[T](observable: Any): Stream[T] = js.native // @TODO ES6 observable?

  // Merge

  @inline def merge[T, EE <: Exception](streams: XStream[T, EE]*): XStream[T, EE] =
    RawXStream.merge(streams: _*)

  // Combine

  // @TODO[API] How can we allow for different kinds of errors?

  @inline def combine[T1, T2, EE <: Exception](
    stream1: XStream[T1, EE],
    stream2: XStream[T2, EE]
  ): XStream[(T1, T2), EE] =
    RawXStream
      .combine(stream1, stream2)
      .mapJs(JSArrayToTuple2[T1, T2] _)

  @inline def combine[T1, T2, T3, EE <: Exception](
    stream1: XStream[T1, EE],
    stream2: XStream[T2, EE],
    stream3: XStream[T3, EE]
  ): XStream[(T1, T2, T3), EE] =
    RawXStream
      .combine(stream1, stream2, stream3)
      .mapJs(JSArrayToTuple3[T1, T2, T3] _)

  @inline def combine[T1, T2, T3, T4, EE <: Exception](
    stream1: XStream[T1, EE],
    stream2: XStream[T2, EE],
    stream3: XStream[T3, EE],
    stream4: XStream[T4, EE]
  ): XStream[(T1, T2, T3, T4), EE] =
    RawXStream
      .combine(stream1, stream2, stream3, stream4)
      .mapJs(JSArrayToTuple4[T1, T2, T3, T4] _)

  //
  //  @inline def combine[T1, T2, T3, T4, T5](
  //    stream1: XStream[T1], stream2: XStream[T2], stream3: XStream[T3], stream4: XStream[T4], stream5: XStream[T5]
  //  ): Tuple5Stream[T1, T2, T3, T4, T5] = {
  //    new Tuple5Stream(
  //      RawStream.combine(stream1.rawStream, stream2.rawStream, stream3.rawStream, stream4.rawStream, stream5.rawStream)
  //        .map((arr: js.Array[T1 | T2 | T3 | T4 | T5]) => jsArrayToTuple5[T1, T2, T3, T4, T5](arr))
  //    )
  //  }

  @inline private def JSArrayToTuple2[T1, T2](arr: js.Array[T1 | T2]): (T1, T2) = {
    (arr(0).asInstanceOf[T1], arr(1).asInstanceOf[T2])
  }

  @inline private def JSArrayToTuple3[T1, T2, T3](arr: js.Array[T1 | T2 | T3]): (T1, T2, T3) = {
    (arr(0).asInstanceOf[T1], arr(1).asInstanceOf[T2], arr(2).asInstanceOf[T3])
  }

  @inline private def JSArrayToTuple4[T1, T2, T3, T4](arr: js.Array[T1 | T2 | T3 | T4]): (T1, T2, T3, T4) = {
    (arr(0).asInstanceOf[T1], arr(1).asInstanceOf[T2], arr(2).asInstanceOf[T3], arr(3).asInstanceOf[T4])
  }

  //  @inline private def JSArrayToTuple5[T1, T2, T3, T4, T5](arr: js.Array[T1 | T2 | T3 | T4 | T5]): (T1, T2, T3, T4, T5) = {
  //    (arr(0).asInstanceOf[T1], arr(1).asInstanceOf[T2], arr(2).asInstanceOf[T3], arr(3).asInstanceOf[T4], arr(4).asInstanceOf[T5])
  //  }
}
