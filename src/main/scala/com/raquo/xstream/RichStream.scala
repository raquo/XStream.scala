package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

class RichStream[+T] (
  val stream: XStream[T]
) extends AnyVal {

  @inline def addListener(listener: Listener[T]): Unit = {
    stream.addListener(listener)
  }

  @inline def removeListener(listener: Listener[T]): Unit = {
    stream.removeListener(listener)
  }

  @inline def subscribe[T2 >: T](listener: Listener[T2]): Subscription[T2] = {
    stream.subscribe(listener)
  }

  @inline def map[U](project: T => U): XStream[U] = {
    stream.jsMap(project)
  }

  @inline def filter(passes: T => Boolean): XStream[T] = {
    stream.jsFilter(passes)
  }

  @inline def filterByValue[T2 >: T](value: T2): XStream[T] = {
    stream.jsFilter((v: T) => v == value)
  }

  @inline def fold[R](accumulate: (R, T) => R, seed: R): MemoryStream[R] = {
    stream.jsFold(accumulate, seed)
  }

  // @TODO[API] EE can be just E, but `|` breaks variance
  @inline def replaceError[T2 >: T](
    replace: Exception | js.Error => XStream[T2]
  ): XStream[T2] = {
    stream.jsReplaceError(replace)
  }

  @inline def compose[T2, ResultStream <: XStream[T2]](
    operator: XStream[T] => ResultStream
  ): ResultStream = {
    stream.jsCompose[T2, ResultStream]((thisStream: XStream[T]) => operator(thisStream))
  }

  @JSName("debug")
  @inline def debugWithSpy(spy: T => Unit): XStream[T] = {
    stream.jsDebugWithSpy(spy)
  }

  @inline def debugger(): XStream[T] = {
    stream.jsDebugWithSpy((value: T) => js.debugger())
  }

  @inline def setDebugListener(listener: Listener[T]): Unit = {
    stream.setDebugListener(listener)
  }
}
