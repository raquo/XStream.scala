package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

/** @see https://github.com/staltz/xstream */
@js.native
trait XStream[+T, +E] extends js.Object {

  def addListener(listener: Listener[T, E]): Unit = js.native

  def removeListener(listener: Listener[T, E]): Unit = js.native

  def subscribe[T2 >: T, E2 >: E](listener: Listener[T2, E2]): Subscription[T2, E2] = js.native

  @JSName("map")
  def mapJs[U](project: js.Function1[T, U]): XStream[U, E] = js.native

  def mapTo[U](projectedValue: U): XStream[U, E] = js.native

  def filter(passes: js.Function1[T, Boolean]): XStream[T, E] = js.native

  def take(amount: Int): XStream[T, E] = js.native

  def drop(amount: Int): XStream[T, E] = js.native

  def last(): XStream[T, E] = js.native

  def startWith[U >: T](initial: U): MemoryStream[U, E] = js.native

  def endWhen(other: XStream[_, _]): XStream[T, E] = js.native

  def fold[R](accumulate: js.Function2[R, T, R], seed: R): MemoryStream[R, E] = js.native

  def replaceError[U >: T, E2](replace: js.Function1[E, XStream[U, E2]]): XStream[U, E2] = js.native

  @JSName("flatten")
  private[xstream] def flattenJs[T2, E2](): XStream[T2, E2] = js.native

  def compose[U, E2](operator: js.Function1[XStream[T, E], XStream[U, E2]]): XStream[U, E2] = js.native

  def compose[U, E2](operator: js.Function1[XStream[T, E2], MemoryStream[U, E2]]): MemoryStream[U, E2] = js.native

  def remember(): MemoryStream[T, E] = js.native

  def debug(spy: js.Function1[T, Any]): XStream[T, E] = js.native

  def debug(label: String): XStream[T, E] = js.native

  def debug(): XStream[T, E] = js.native

  def imitate[U >: T, E2 >: E](target: XStream[U, E2]): Unit = js.native

  private[xstream] def shamefullySendNext[U >: T](value: U): Unit = js.native

  private[xstream] def shamefullySendError[E2 >: E](error: E2): Unit = js.native

  private[xstream] def shamefullySendComplete(): Unit = js.native

  def setDebugListener(listener: Listener[T, E]): Unit = js.native
}

/** @see https://github.com/staltz/xstream */
object XStream {

  // @TODO Method names are not very consistent: merge() makes sense on its own, but of() needs XStream.of for context.

  // Simple streams

  @inline def of[T, E](value: T): XStream[T, E] =
    RawXStream.of(value)

  @inline def never(): XStream[Nothing, Nothing] =
    RawXStream.never()

  @inline def empty(): XStream[Nothing, Nothing] =
    RawXStream.empty()

  @inline def throwError[E](error: E): XStream[Nothing, E] =
    RawXStream.`throw`(error)

  @inline def periodic(period: Int): XStream[Int, Nothing] =
    RawXStream.periodic(period)

  // create & createWithMemory

  @inline def create[T, E](): XStream[T, E] =
    RawXStream.create()

  @inline def create[T, E](producer: Producer[T, E]): XStream[T, E] =
    RawXStream.create(producer)

  @inline def createWithMemory[T, E](): MemoryStream[T, E] =
    RawXStream.createWithMemory()

  @inline def createWithMemory[T, E](producer: Producer[T, E]): MemoryStream[T, E] =
    RawXStream.createWithMemory(producer)

  // from<X>

  @inline def fromSeq[T](seq: Seq[T]): XStream[T, Nothing] =
    RawXStream.fromArray(seq.toJSArray)

  @inline def fromPromise[T, E](promise: js.Promise[T]): XStream[T, E] =
    RawXStream.fromPromise(promise)

  @inline def fromJSArray[T](array: js.Array[T]): XStream[T, Nothing] =
    RawXStream.fromArray(array)

  //  @inline def fromJsObservable[T](observable: Any): Stream[T] = js.native // @TODO ES6 observable?

  // Merge

  @inline def merge[T, E](streams: XStream[T, E]*): XStream[T, E] =
    RawXStream.merge(streams: _*)

  // Combine

  // @TODO[API] How can we allow for different kinds of errors?

  @inline def combine[T1, T2, E](
    stream1: XStream[T1, E],
    stream2: XStream[T2, E]
  ): XStream[(T1, T2), E] =
    RawXStream
      .combine(stream1, stream2)
      .mapJs(JSArrayToTuple2[T1, T2] _)

  @inline def combine[T1, T2, T3, E](
    stream1: XStream[T1, E],
    stream2: XStream[T2, E],
    stream3: XStream[T3, E]
  ): XStream[(T1, T2, T3), E] =
    RawXStream
      .combine(stream1, stream2, stream3)
      .mapJs(JSArrayToTuple3[T1, T2, T3] _)

  @inline def combine[T1, T2, T3, T4, E](
    stream1: XStream[T1, E],
    stream2: XStream[T2, E],
    stream3: XStream[T3, E],
    stream4: XStream[T4, E]
  ): XStream[(T1, T2, T3, T4), E] =
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
