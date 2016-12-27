package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@js.native
@JSImport("xstream", JSImport.Default)
object RawXStream extends js.Object {

  def create[T, E <: js.Error](producer: Producer[T, E]): XStream[T, E] = js.native

  def create[T, E <: js.Error](): XStream[T, E] = js.native

  def createWithMemory[T, E <: js.Error](): MemoryStream[T, E] = js.native

  def createWithMemory[T, E <: js.Error](producer: Producer[T, E]): MemoryStream[T, E] = js.native

  def never(): XStream[Nothing, Nothing] = js.native

  def empty(): XStream[Nothing, Nothing] = js.native

  def `throw`[E <: js.Error](error: E): XStream[Nothing, E] = js.native

  def of[T](values: T*): XStream[T, Nothing] = js.native // @TODO is this correct signature for TS `(...items: Array<T>)`?

  def fromArray[T, E <: js.Error](array: js.Array[T]): XStream[T, Nothing] = js.native

  def fromPromise[T, E <: js.Error](promise: js.Promise[T]): XStream[T, E] = js.native

  def fromObservable[T, E <: js.Error](observable: Any): XStream[T, E] = js.native // @TODO What observable? Why??

  def periodic(period: Int): XStream[Int, Nothing] = js.native

  def merge[T, E <: js.Error](streams: XStream[T, E]*): XStream[T, E] = js.native

  def merge[T, E <: js.Error](streams: js.Array[XStream[T, E]]): XStream[T, E] = js.native // @TODO does this do what I think it does?

  // @TODO Add up to... 8? variations

  def combine[T, E <: js.Error](streams: XStream[T, E]*): XStream[js.Array[T], E] = js.native // @TODO does this do what I think it does?

  def combine[T1, T2, E <: js.Error](
    stream1: XStream[T1, E],
    stream2: XStream[T2, E]
  ): XStream[js.Array[T1 | T2], E] = js.native

  def combine[T1, T2, T3, E <: js.Error](
    stream1: XStream[T1, E],
    stream2: XStream[T2, E],
    stream3: XStream[T3, E]
  ): XStream[js.Array[T1 | T2 | T3], E] = js.native

  def combine[T1, T2, T3, T4, E <: js.Error](
    stream1: XStream[T1, E],
    stream2: XStream[T2, E],
    stream3: XStream[T3, E],
    stream4: XStream[T4, E]
  ): XStream[js.Array[T1 | T2 | T3 | T4], E] = js.native

  def combine[T1, T2, T3, T4, T5, E <: js.Error](
    stream1: XStream[T1, E],
    stream2: XStream[T2, E],
    stream3: XStream[T3, E],
    stream4: XStream[T4, E],
    stream5: XStream[T5, E]
  ): XStream[js.Array[T1 | T2 | T3 | T4 | T5], E] = js.native
}
