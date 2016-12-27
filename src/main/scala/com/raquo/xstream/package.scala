package com.raquo

import scala.scalajs.js

package object xstream {

  implicit class RichStream[+T, +E <: js.Error] (
    val stream: XStream[T, E]
  ) extends AnyVal {

    def addListener(listener: Listener[T, E]): Unit =
      stream.addListener(listener)

    def removeListener(listener: Listener[T, E]): Unit =
      stream.removeListener(listener)

    def subscribe[T2 >: T, E2 >: E <: js.Error](listener: Listener[T2, E2]): Subscription[T2, E2] =
      stream.subscribe(listener)

    def map[U](project: T => U): XStream[U, E] = stream.mapJs(project)

    def filter(passes: T => Boolean): XStream[T, E] =
      stream.filterJs(passes)

    def fold[R](accumulate: (R, T) => R, seed: R): MemoryStream[R, E] =
      stream.foldJs(accumulate, seed)

    def replaceError[T2 >: T, E2 >: E <: js.Error](replace: E => XStream[T2, E2]): XStream[T2, E2] =
      stream.replaceErrorJs((error: E) => replace(error))

    def compose[T2, E2 <: js.Error, ResultStream <: XStream[T2, E2]](
      operator: XStream[T, E] => ResultStream
    ): ResultStream = {
      stream.composeJs[T2, E2, ResultStream]((thisStream: XStream[T, E]) => operator(thisStream))
    }

    def debug(spy: T => Unit): XStream[T, E] =
      stream.debugJs(spy)

    def debugger(): XStream[T, E] =
      stream.debugJs((value: T) => js.debugger())

    def setDebugListener(listener: Listener[T, E]): Unit =
      stream.setDebugListener(listener)
  }

  implicit class RichMemoryStream[+T, +E <: js.Error] (
    val memoryStream: MemoryStream[T, E]
  ) extends AnyVal {

    def map[U](project: T => U): MemoryStream[U, E] =
      memoryStream.mapJs(project)

    def replaceError[U >: T, E2 <: js.Error](replace: E => XStream[U, E2]): MemoryStream[T, E2] =
      memoryStream.replaceErrorJs(replace)

    def debug(spy: T => Any): MemoryStream[T, E] =
      memoryStream.debugJs(spy)
  }

  implicit class MetaStream[T, E <: js.Error] (
    val streamOfStreams: XStream[XStream[T, E], Nothing]
  ) extends AnyVal {

    def flatten: XStream[T, E] = streamOfStreams.flattenJs[T, E]()
  }

  implicit class TupleStream2[+T1, +T2, +E <: js.Error] (
    val tupleStream: XStream[(T1, T2), E]
  ) extends AnyVal {

    @inline def map[U](project: (T1, T2) => U): XStream[U, E] =
      tupleStream.mapJs(project.tupled)

    @inline def filter(passes: (T1, T2) => Boolean): XStream[(T1, T2), E] =
      tupleStream.filterJs(passes.tupled)

    @inline def debug(spy: (T1, T2) => Any): XStream[(T1, T2), E] =
      tupleStream.debugJs(spy.tupled)
  }

  implicit class TupleStream3[+T1, +T2, +T3, +E <: js.Error] (
    val tupleStream: XStream[(T1, T2, T3), E]
  ) extends AnyVal {

    @inline def map[U](project: (T1, T2, T3) => U): XStream[U, E] =
      tupleStream.mapJs(project.tupled)

    @inline def filter(passes: (T1, T2, T3) => Boolean): XStream[(T1, T2, T3), E] =
      tupleStream.filterJs(passes.tupled)

    @inline def debug(spy: (T1, T2, T3) => Any): XStream[(T1, T2, T3), E] =
      tupleStream.debugJs(spy.tupled)
  }

  implicit class TupleStream4[+T1, +T2, +T3, +T4, +E <: js.Error] (
    val tupleStream: XStream[(T1, T2, T3, T4), E]
  ) extends AnyVal {

    @inline def map[U](project: (T1, T2, T3, T4) => U): XStream[U, E] =
      tupleStream.mapJs(project.tupled)

    @inline def filter(passes: (T1, T2, T3, T4) => Boolean): XStream[(T1, T2, T3, T4), E] =
      tupleStream.filterJs(passes.tupled)

    @inline def debug(spy: (T1, T2, T3, T4) => Any): XStream[(T1, T2, T3, T4), E] =
      tupleStream.debugJs(spy.tupled)
  }
}
