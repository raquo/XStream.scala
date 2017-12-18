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

  @inline def fold[R](accumulate: (R, T) => R, seed: R): MemoryStream[R] = {
    stream.jsFold(accumulate, seed)
  }

  @inline def replaceError[T2 >: T](
    replace: Exception | js.Error => XStream[T2]
  ): XStream[T2] = {
    stream.jsReplaceError(replace)
  }


  // @TODO try this fix: https://stackoverflow.com/questions/6888136/type-inferred-to-nothing-in-scala

  @inline def compose[T2, ResultStream[T2] <: XStream[T2]](
    operator: XStream[T] => ResultStream[T2]
  ): ResultStream[T2] = {
    stream.jsCompose[T2, ResultStream[T2]]((thisStream: XStream[T]) => operator(thisStream))
  }

  // @TODO not sure which signature is better... maybe rename the other compose to composeWithMemory, but then.... eh

//  @inline def compose[T2](
//    operator: XStream[T] => XStream[T2]
//  ): XStream[T2] = {
//    stream.jsCompose[T2, XStream[T2]](operator)
//  }
//
//  @inline def compose[T2](
//    operator: XStream[T] => MemoryStream[T2]
//  ): MemoryStream[T2] = {
//    stream.jsCompose[T2, MemoryStream[T2]](operator)
//  }

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
