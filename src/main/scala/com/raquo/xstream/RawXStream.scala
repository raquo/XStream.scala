package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@js.native
@JSImport("xstream", JSImport.Default)
object RawXStream extends js.Object {

  def create[T, EE <: Exception](producer: Producer[T, EE]): XStream[T, EE] = js.native

  def create[T, EE <: Exception](): XStream[T, EE] = js.native

  def createWithMemory[T, EE <: Exception](): MemoryStream[T, EE] = js.native

  def createWithMemory[T, EE <: Exception](producer: Producer[T, EE]): MemoryStream[T, EE] = js.native

  def never(): XStream[Nothing, Nothing] = js.native

  def empty(): XStream[Nothing, Nothing] = js.native

  private[xstream] def `throw`[EE <: Exception](error: EE): XStream[Nothing, EE] = js.native

  private[xstream] def `throw`(error: Exception | js.Error): XStream[Nothing, Nothing] = js.native

  def of[T](values: T*): XStream[T, Nothing] = js.native // @TODO is this correct signature for TS `(...items: Array<T>)`?

  def fromArray[T](array: js.Array[T]): XStream[T, Nothing] = js.native

  def fromPromise[T, EE <: Exception](promise: js.Promise[T]): XStream[T, EE] = js.native

  def fromObservable[T, EE <: Exception](observable: Any): XStream[T, EE] = js.native // @TODO add type for ES6 observable

  def periodic(period: Int): XStream[Int, Nothing] = js.native

  def merge[T, EE <: Exception](streams: XStream[T, EE]*): XStream[T, EE] = js.native

  def merge[T, EE <: Exception](streams: js.Array[XStream[T, EE]]): XStream[T, EE] = js.native // @TODO does this do what I think it does?

  // @TODO Add up to... 8? variations

  def combine[T, EE <: Exception](streams: XStream[T, EE]*): XStream[js.Array[T], EE] = js.native // @TODO does this do what I think it does?

  def combine[T1, T2, EE <: Exception](
    stream1: XStream[T1, EE],
    stream2: XStream[T2, EE]
  ): XStream[js.Array[T1 | T2], EE] = js.native

  def combine[T1, T2, T3, EE <: Exception](
    stream1: XStream[T1, EE],
    stream2: XStream[T2, EE],
    stream3: XStream[T3, EE]
  ): XStream[js.Array[T1 | T2 | T3], EE] = js.native

  def combine[T1, T2, T3, T4, EE <: Exception](
    stream1: XStream[T1, EE],
    stream2: XStream[T2, EE],
    stream3: XStream[T3, EE],
    stream4: XStream[T4, EE]
  ): XStream[js.Array[T1 | T2 | T3 | T4], EE] = js.native

  def combine[T1, T2, T3, T4, T5, EE <: Exception](
    stream1: XStream[T1, EE],
    stream2: XStream[T2, EE],
    stream3: XStream[T3, EE],
    stream4: XStream[T4, EE],
    stream5: XStream[T5, EE]
  ): XStream[js.Array[T1 | T2 | T3 | T4 | T5], EE] = js.native
}
