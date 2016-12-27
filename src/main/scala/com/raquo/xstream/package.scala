package com.raquo

import scala.scalajs.js

package object xstream {

  implicit class RichStream[+T, +E] (val stream: XStream[T, E]) extends AnyVal {

    def addListener(listener: Listener[T, E]): Unit =
      stream.addListener(listener)

    def removeListener(listener: Listener[T, E]): Unit =
      stream.removeListener(listener)

    def subscribe[T2 >: T, E2 >: E](listener: Listener[T2, E2]): Subscription[T2, E2] =
      stream.subscribe(listener)

    def map[U](project: T => U): XStream[U, E] = stream.mapJs(project)

    def filter(passes: T => Boolean): XStream[T, E] =
      stream.filter(passes)

    def fold[R](accumulate: (R, T) => R, seed: R): MemoryStream[R, E] =
      stream.fold(accumulate, seed)

    def replaceError[T2 >: T, E2 >: E](replace: E => XStream[T2, E2]): XStream[T2, E2] =
      stream.replaceError((error: E) => replace(error))

    def compose[T2, E2](operator: XStream[T, E] => XStream[T2, E2]): XStream[T2, E2] = {
      stream.compose((thisStream: XStream[T, E]) => operator(thisStream))
    }

    def debug(spy: T => Unit): XStream[T, E] =
      stream.debug(spy)

    def debugger(): XStream[T, E] =
      stream.debug((value: T) => js.debugger())

    def setDebugListener(listener: Listener[T, E]): Unit =
      stream.setDebugListener(listener)
  }

  implicit class MetaStream[T, E] (val streamOfStreams: XStream[XStream[T, E], Nothing]) extends AnyVal {

    def flatten: XStream[T, E] = streamOfStreams.flattenJs[T, E]()
  }

  implicit class TupleStream2[+T1, +T2, +E](val tupleStream: XStream[(T1, T2), E]) extends AnyVal {

    @inline def map[U](project: (T1, T2) => U): XStream[U, E] =
      tupleStream.mapJs(project.tupled)

    @inline def filter(passes: (T1, T2) => Boolean): XStream[(T1, T2), E] =
      tupleStream.filter(passes.tupled)

    @inline def debug(spy: (T1, T2) => Any): XStream[(T1, T2), E] =
      tupleStream.debug(spy.tupled)
  }

  implicit class TupleStream3[+T1, +T2, +T3, +E](val tupleStream: XStream[(T1, T2, T3), E]) extends AnyVal {

    @inline def map[U](project: (T1, T2, T3) => U): XStream[U, E] =
      tupleStream.mapJs(project.tupled)

    @inline def filter(passes: (T1, T2, T3) => Boolean): XStream[(T1, T2, T3), E] =
      tupleStream.filter(passes.tupled)

    @inline def debug(spy: (T1, T2, T3) => Any): XStream[(T1, T2, T3), E] =
      tupleStream.debug(spy.tupled)
  }

  implicit class TupleStream4[+T1, +T2, +T3, +T4, +E](val tupleStream: XStream[(T1, T2, T3, T4), E]) extends AnyVal {

    @inline def map[U](project: (T1, T2, T3, T4) => U): XStream[U, E] =
      tupleStream.mapJs(project.tupled)

    @inline def filter(passes: (T1, T2, T3, T4) => Boolean): XStream[(T1, T2, T3, T4), E] =
      tupleStream.filter(passes.tupled)

    @inline def debug(spy: (T1, T2, T3, T4) => Any): XStream[(T1, T2, T3, T4), E] =
      tupleStream.debug(spy.tupled)
  }
}
