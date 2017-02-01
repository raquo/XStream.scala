package com.raquo

import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.|

package object xstream {

  // @TODO[Elegance] Move rich classes (ha!) out of this mess

  implicit class RichStream[+T, +EE <: Exception] (
    val stream: XStream[T, EE]
  ) extends AnyVal {

    @inline def addListener(listener: Listener[T, EE]): Unit =
      stream.addListener(listener)

    @inline def removeListener(listener: Listener[T, EE]): Unit =
      stream.removeListener(listener)

    @inline def subscribe[T2 >: T, EE2 >: EE <: Exception](listener: Listener[T2, EE2]): Subscription[T2, EE2] =
      stream.subscribe(listener)

    @inline def map[U](project: T => U): XStream[U, EE] = stream.mapJs(project)

    @inline def filter(passes: T => Boolean): XStream[T, EE] =
      stream.filterJs(passes)

    @inline def fold[R](accumulate: (R, T) => R, seed: R): MemoryStream[R, EE] =
      stream.foldJs(accumulate, seed)

    // @TODO[Elegance] Simplify this – remove classTag, just check for js.Error

    // @TODO[Integrity] ExpErr should be just EE, but I can't get ClassTag on it because this is a value class
    @inline def replaceExpectedErrors[T2 >: T, ExpErr >: EE <: Exception : ClassTag](
      replace: ExpErr => XStream[T2, Nothing]
    ): XStream[T2, Nothing] = {
      val expectedErrorClass = implicitly[ClassTag[ExpErr]].runtimeClass
      stream.replaceAllErrorsJs((error: Exception | js.Error) => {
        if (expectedErrorClass.isInstance(error)) {
          replace(error.asInstanceOf[ExpErr])
        } else {
          XStream.rethrowUnexpectedError(error.asInstanceOf[js.Error])
        }
      })
    }

    // @TODO[API] EE can be just E, but `|` breaks variance
    @inline def replaceAllErrors[T2 >: T](
      replace: Exception | js.Error => XStream[T2, Nothing]
    ): XStream[T2, Nothing] = {
      stream.replaceAllErrorsJs(replace)
    }

    @inline def compose[T2, EE2 <: Exception, ResultStream <: XStream[T2, EE2]](
      operator: XStream[T, EE] => ResultStream
    ): ResultStream = {
      stream.composeJs[T2, EE2, ResultStream]((thisStream: XStream[T, EE]) => operator(thisStream))
    }

    @inline def debug(spy: T => Unit): XStream[T, EE] =
      stream.debugJs(spy)

    @inline def debugger(): XStream[T, EE] =
      stream.debugJs((value: T) => js.debugger())

    @inline def setDebugListener(listener: Listener[T, EE]): Unit =
      stream.setDebugListener(listener)
  }

  implicit class RichMemoryStream[+T, +EE <: Exception] (
    val memoryStream: MemoryStream[T, EE]
  ) extends AnyVal {

    @inline def map[U](project: T => U): MemoryStream[U, EE] =
      memoryStream.mapJs(project)

    @inline def replaceAllErrors[T2 >: T](
      replace: Exception | js.Error => XStream[T2, Nothing]
    ): MemoryStream[T2, Nothing] = {
      memoryStream.replaceAllErrorsJs(replace)
    }

    @inline def debug(spy: T => Any): MemoryStream[T, EE] =
      memoryStream.debugJs(spy)
  }

  // @TODO[Elegance] can/should we replace this with evidence implicit params?
  implicit class MetaStream[T, EE <: Exception] (
    val streamOfStreams: XStream[XStream[T, EE], Nothing]
  ) extends AnyVal {

    @inline def flatten: XStream[T, EE] = streamOfStreams.flattenJs[T, EE]()
  }

  implicit class TupleStream2[+T1, +T2, +EE <: Exception] (
    val tupleStream: XStream[(T1, T2), EE]
  ) extends AnyVal {

    @inline def map[U](project: (T1, T2) => U): XStream[U, EE] =
      tupleStream.mapJs(project.tupled)

    @inline def filter(passes: (T1, T2) => Boolean): XStream[(T1, T2), EE] =
      tupleStream.filterJs(passes.tupled)

    @inline def debug(spy: (T1, T2) => Any): XStream[(T1, T2), EE] =
      tupleStream.debugJs(spy.tupled)
  }

  implicit class TupleStream3[+T1, +T2, +T3, +EE <: Exception] (
    val tupleStream: XStream[(T1, T2, T3), EE]
  ) extends AnyVal {

    @inline def map[U](project: (T1, T2, T3) => U): XStream[U, EE] =
      tupleStream.mapJs(project.tupled)

    @inline def filter(passes: (T1, T2, T3) => Boolean): XStream[(T1, T2, T3), EE] =
      tupleStream.filterJs(passes.tupled)

    @inline def debug(spy: (T1, T2, T3) => Any): XStream[(T1, T2, T3), EE] =
      tupleStream.debugJs(spy.tupled)
  }

  implicit class TupleStream4[+T1, +T2, +T3, +T4, +EE <: Exception] (
    val tupleStream: XStream[(T1, T2, T3, T4), EE]
  ) extends AnyVal {

    @inline def map[U](project: (T1, T2, T3, T4) => U): XStream[U, EE] =
      tupleStream.mapJs(project.tupled)

    @inline def filter(passes: (T1, T2, T3, T4) => Boolean): XStream[(T1, T2, T3, T4), EE] =
      tupleStream.filterJs(passes.tupled)

    @inline def debug(spy: (T1, T2, T3, T4) => Any): XStream[(T1, T2, T3, T4), EE] =
      tupleStream.debugJs(spy.tupled)
  }
}
