package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@js.native
@JSImport("xstream", JSImport.Default)
object RawXStream extends js.Object {

  def create[T, EE <: Exception](producer: Producer[T, EE]): EStream[T, EE] = js.native

  def create[T, EE <: Exception](): EStream[T, EE] = js.native

  def never(): XStream[Nothing] = js.native

  def empty(): XStream[Nothing] = js.native

  private[xstream] def `throw`[EE <: Exception](error: EE): EStream[Nothing, EE] = js.native

  private[xstream] def `throw`(error: Exception | js.Error): XStream[Nothing] = js.native

  def of[T](values: T*): XStream[T] = js.native // @TODO is this correct signature for TS `(...items: Array<T>)`?

  def fromArray[T](array: js.Array[T]): XStream[T] = js.native

  def fromPromise[T, EE <: Exception](promise: js.Promise[T]): EStream[T, EE] = js.native

  def fromObservable[T, EE <: Exception](observable: Any): EStream[T, EE] = js.native // @TODO add type for ES6 observable

  def periodic(period: Int): XStream[Int] = js.native

  def merge[T, EE <: Exception](streams: EStream[T, EE]*): EStream[T, EE] = js.native

  def merge[T, EE <: Exception](streams: js.Array[EStream[T, EE]]): EStream[T, EE] = js.native // @TODO does this do what I think it does?

  // @TODO Add up to... 8? variations

  def combine[T, EE <: Exception](streams: EStream[T, EE]*): EStream[js.Array[T], EE] = js.native // @TODO does this do what I think it does?

  def combine[T1, T2, EE <: Exception](
    stream1: EStream[T1, EE],
    stream2: EStream[T2, EE]
  ): EStream[js.Array[T1 | T2], EE] = js.native

  def combine[T1, T2, T3, EE <: Exception](
    stream1: EStream[T1, EE],
    stream2: EStream[T2, EE],
    stream3: EStream[T3, EE]
  ): EStream[js.Array[T1 | T2 | T3], EE] = js.native

  def combine[T1, T2, T3, T4, EE <: Exception](
    stream1: EStream[T1, EE],
    stream2: EStream[T2, EE],
    stream3: EStream[T3, EE],
    stream4: EStream[T4, EE]
  ): EStream[js.Array[T1 | T2 | T3 | T4], EE] = js.native

  def combine[T1, T2, T3, T4, T5, EE <: Exception](
    stream1: EStream[T1, EE],
    stream2: EStream[T2, EE],
    stream3: EStream[T3, EE],
    stream4: EStream[T4, EE],
    stream5: EStream[T5, EE]
  ): EStream[js.Array[T1 | T2 | T3 | T4 | T5], EE] = js.native
}
