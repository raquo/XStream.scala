package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSName

/** @see https://github.com/staltz/xstream */
@js.native
trait XStream[+T] extends js.Object {

  def addListener(listener: Listener[T]): Unit = js.native

  def removeListener(listener: Listener[T]): Unit = js.native

  def subscribe[T2 >: T](listener: Listener[T2]): Subscription[T2] = js.native

  @JSName("map")
  def jsMap[U](project: js.Function1[T, U]): XStream[U] = js.native

  def mapTo[U](projectedValue: U): XStream[U] = js.native

  @JSName("filter")
  def jsFilter(passes: js.Function1[T, Boolean]): XStream[T] = js.native

  def take(amount: Int): XStream[T] = js.native

  def drop(amount: Int): XStream[T] = js.native

  def last(): XStream[T] = js.native

  def startWith[U >: T](initial: U): MemoryStream[U] = js.native

  def endWhen(other: XStream[_]): XStream[T] = js.native

  @JSName("fold")
  def jsFold[R](accumulate: js.Function2[R, T, R], seed: R): MemoryStream[R] = js.native

  @JSName("replaceError")
  def jsReplaceError[U >: T](
    replace: js.Function1[Exception | js.Error, XStream[U]]
  ): XStream[U] = js.native

  /** This is private because it works only on streams of streams. See [[MetaStream.flatten]] */
  @JSName("flatten")
  private[xstream] def jsFlatten[T2](): XStream[T2] = js.native

  @JSName("compose")
  def jsCompose[T2, ResultStream <: XStream[T2]](
    operator: js.Function1[XStream[T], ResultStream]
  ): ResultStream = js.native

  def remember(): MemoryStream[T] = js.native

  @JSName("debug")
  def jsDebugWithSpy(spy: js.Function1[T, Any]): XStream[T] = js.native

  @JSName("debug")
  def debugWithLabel(label: String): XStream[T] = js.native

  def debug(): XStream[T] = js.native

  def setDebugListener(listener: Listener[T]): Unit = js.native

  // @TODO[Integrity] Seems that this violates covariance. Do we eve need this? Maybe provide similar functionality via .compose?
  private[xstream] def imitate[U >: T](target: XStream[U]): Unit = js.native

  // @TODO[Integrity] Seems that this violates covariance. However, this is only exposed on ShamefulStream which is not covariant. Is that ok?
  private[xstream] def shamefullySendNext[U >: T](value: U): Unit = js.native

  private[xstream] def shamefullySendError(error: Exception | js.Error): Unit = js.native

  private[xstream] def shamefullySendComplete(): Unit = js.native
}

/** @see https://github.com/staltz/xstream */
object XStream {

  // Simple streams

  @inline def of[T](value: T): XStream[T] = {
    RawXStream.of(value)
  }

  @inline def never(): XStream[Nothing] = {
    RawXStream.never()
  }

  @inline def empty(): XStream[Nothing] = {
    RawXStream.empty()
  }

  /** See also [[XStream.throwError]] */
  @inline def throwError(error: Exception | js.Error): XStream[Nothing] = {
    RawXStream.`throw`(error)
  }

  @inline def periodic(period: Int): XStream[Int] = {
    RawXStream.periodic(period)
  }

  // create

  @inline def create[T](): XStream[T] = {
    RawXStream.create()
  }

  @inline def create[T](producer: Producer[T]): XStream[T] = {
    RawXStream.create(producer)
  }

  // from<X>

  @inline def fromSeq[T](seq: Seq[T]): XStream[T] = {
    RawXStream.fromArray(seq.toJSArray)
  }

  /// @TODO[API] does it actually make sense to have no errors in a promise?
  @inline def fromPromise[T](promise: js.Promise[T]): XStream[T] = {
    RawXStream.fromPromise(promise)
  }

  @inline def fromJSArray[T](array: js.Array[T]): XStream[T] = {
    RawXStream.fromArray(array)
  }

  //  @inline def fromJsObservable[T](observable: Any): Stream[T] = js.native // @TODO ES6 observable?

  // Merge

  @inline def merge[T](streams: XStream[T]*): XStream[T] = {
    RawXStream.merge(streams: _*)
  }

  // Combine

  @inline def combine[T1, T2](
    stream1: XStream[T1],
    stream2: XStream[T2]
  ): XStream[(T1, T2)] = {
    RawXStream
      .combine(stream1, stream2)
      .jsMap(TupleOps.JSArrayToTuple2[T1, T2] _)
  }

  @inline def combine[T1, T2, T3](
    stream1: XStream[T1],
    stream2: XStream[T2],
    stream3: XStream[T3]
  ): XStream[(T1, T2, T3)] = {
    RawXStream
      .combine(stream1, stream2, stream3)
      .jsMap(TupleOps.JSArrayToTuple3[T1, T2, T3] _)
  }

  @inline def combine[T1, T2, T3, T4](
    stream1: XStream[T1],
    stream2: XStream[T2],
    stream3: XStream[T3],
    stream4: XStream[T4]
  ): XStream[(T1, T2, T3, T4)] = {
    RawXStream
      .combine(stream1, stream2, stream3, stream4)
      .jsMap(TupleOps.JSArrayToTuple4[T1, T2, T3, T4] _)
  }

  //
  //  @inline def combine[T1, T2, T3, T4, T5](
  //    stream1: XStream[T1], stream2: XStream[T2], stream3: XStream[T3], stream4: XStream[T4], stream5: XStream[T5]
  //  ): Tuple5Stream[T1, T2, T3, T4, T5] = {
  //    new Tuple5Stream(
  //      RawStream.combine(stream1.rawStream, stream2.rawStream, stream3.rawStream, stream4.rawStream, stream5.rawStream)
  //        .map((arr: js.Array[T1 | T2 | T3 | T4 | T5]) => jsArrayToTuple5[T1, T2, T3, T4, T5](arr))
  //    )
  //  }

  //  @inline private def JSArrayToTuple5[T1, T2, T3, T4, T5](arr: js.Array[T1 | T2 | T3 | T4 | T5]): (T1, T2, T3, T4, T5) = {
  //    (arr(0).asInstanceOf[T1], arr(1).asInstanceOf[T2], arr(2).asInstanceOf[T3], arr(3).asInstanceOf[T4], arr(4).asInstanceOf[T5])
  //  }
}
