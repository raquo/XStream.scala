package com.raquo.xstream

import com.raquo.xstream.rawextras.{RawDelay, RawSampleCombine}

class Extras[A](val innerStream: XStream[A]) extends AnyVal {

  @inline def delay(millis: Int = 0): XStream[A] = RawDelay[A](millis)(innerStream)

  def sampleCombine[B](
    streamB: XStream[B]
  ): XStream[(A, B)] = {
    val rawSampleCombine = RawSampleCombine[A, B](streamB)
    innerStream
      .compose(rawSampleCombine)
      .map(TupleOps.JSArrayToTuple2[A, B])
  }

  def sampleCombine[B, C](
    streamB: XStream[B],
    streamC: XStream[C]
  ): XStream[(A, B, C)] = {
    val rawSampleCombine = RawSampleCombine[A, B, C](streamB, streamC)
    innerStream
      .compose(rawSampleCombine)
      .map(TupleOps.JSArrayToTuple3[A, B, C])
  }

  def sampleCombine[B, C, D](
    streamB: XStream[B],
    streamC: XStream[C],
    streamD: XStream[D]
  ): XStream[(A, B, C, D)] = {
    val rawSampleCombine = RawSampleCombine[A, B, C, D](streamB, streamC, streamD)
    innerStream
      .compose(rawSampleCombine)
      .map(TupleOps.JSArrayToTuple4[A, B, C, D])
  }


  // @TODO[API] My own operators – move those out some time
  // @TODO[API] All these should have both XStream and MemoryStream typings

  /** Returns a stream that fires an event when `innerStream` fires, and the event is the latest value from streamB */
  def sample[B](
    streamB: XStream[B]
  ): XStream[B] = {
    val rawSampleCombine = RawSampleCombine[A, B](streamB)
    innerStream
      .compose(rawSampleCombine)
      .map(arr => TupleOps.JSArrayToTuple2[A, B](arr)._2)
  }

  def slide2by1(initial: A): XStream[(A, A)] = {
    innerStream.fold[(A, A)](
      accumulate = (prevPair, next) => (prevPair._2, next),
      seed = (initial, initial)
    )
  }

  def collect[B](pf: PartialFunction[A, B]): XStream[B] = {
    // @TODO[Performance] pf.isDefinedAt is evaluated twice here. I don't think there is a way around it. https://stackoverflow.com/q/4064859/2601788
    innerStream
      .filter(pf.isDefinedAt)
      .map(pf)
  }

  def startWithNone(): MemoryStream[Option[A]] = {
    innerStream
      .map(Some(_))
      .startWith(None)
  }
}
