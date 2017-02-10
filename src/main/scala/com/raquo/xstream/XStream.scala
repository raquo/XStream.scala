package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.JSConverters._

/** @see https://github.com/staltz/xstream */
object XStream extends TupleOps {

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

  /** See also [[EStream.throwExpectedError]] */
  @inline def throwUnexpectedError(error: Exception | js.Error): XStream[Nothing] = {
    RawXStream.`throw`(error)
  }

  @inline def periodic(period: Int): XStream[Int] = {
    RawXStream.periodic(period)
  }

  // create

  @inline def create[T](): XStream[T] = {
    RawXStream.create()
  }

  @inline def create[T](producer: Producer[T, Nothing]): XStream[T] = {
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
      .jsMap(JSArrayToTuple2[T1, T2] _)
  }

  @inline def combine[T1, T2, T3](
    stream1: XStream[T1],
    stream2: XStream[T2],
    stream3: XStream[T3]
  ): XStream[(T1, T2, T3)] = {
    RawXStream
      .combine(stream1, stream2, stream3)
      .jsMap(JSArrayToTuple3[T1, T2, T3] _)
  }

  @inline def combine[T1, T2, T3, T4](
    stream1: XStream[T1],
    stream2: XStream[T2],
    stream3: XStream[T3],
    stream4: XStream[T4]
  ): XStream[(T1, T2, T3, T4)] = {
    RawXStream
      .combine(stream1, stream2, stream3, stream4)
      .jsMap(JSArrayToTuple4[T1, T2, T3, T4] _)
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
