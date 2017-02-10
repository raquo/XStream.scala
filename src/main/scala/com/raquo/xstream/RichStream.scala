package com.raquo.xstream

import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.|

class RichStream[+T, +EE <: Exception] (
  val stream: XStream[T, EE]
) extends AnyVal {

  @inline def addListener(listener: Listener[T, EE]): Unit = {
    stream.addListener(listener)
  }

  @inline def removeListener(listener: Listener[T, EE]): Unit = {
    stream.removeListener(listener)
  }

  @inline def subscribe[T2 >: T, EE2 >: EE <: Exception](listener: Listener[T2, EE2]): Subscription[T2, EE2] = {
    stream.subscribe(listener)
  }

  @inline def map[U](project: T => U): XStream[U, EE] = {
    stream.jsMap(project)
  }

  @inline def filter(passes: T => Boolean): XStream[T, EE] = {
    stream.filterJs(passes)
  }

  @inline def fold[R](accumulate: (R, T) => R, seed: R): MemoryStream[R, EE] = {
    stream.jsFold(accumulate, seed)
  }

  // @TODO[Elegance] Simplify this – remove classTag, just check for js.Error

  // @TODO[Integrity] ExpErr should be just EE, but I can't get ClassTag on it because this is a value class
  @inline def replaceExpectedErrors[T2 >: T, ExpErr >: EE <: Exception : ClassTag](
    replace: ExpErr => XStream[T2, Nothing]
  ): XStream[T2, Nothing] = {
    val expectedErrorClass = implicitly[ClassTag[ExpErr]].runtimeClass
    stream.jsReplaceAllErrors((error: Exception | js.Error) => {
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
    stream.jsReplaceAllErrors(replace)
  }

  @inline def compose[T2, EE2 <: Exception, ResultStream <: XStream[T2, EE2]](
    operator: XStream[T, EE] => ResultStream
  ): ResultStream = {
    stream.jsCompose[T2, EE2, ResultStream]((thisStream: XStream[T, EE]) => operator(thisStream))
  }

  @inline def debug(spy: T => Unit): XStream[T, EE] = {
    stream.debugJs(spy)
  }

  @inline def debugger(): XStream[T, EE] = {
    stream.debugJs((value: T) => js.debugger())
  }

  @inline def setDebugListener(listener: Listener[T, EE]): Unit = {
    stream.setDebugListener(listener)
  }
}
