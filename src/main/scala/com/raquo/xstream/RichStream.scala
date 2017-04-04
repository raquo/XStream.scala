package com.raquo.xstream

import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

class RichStream[+T, +EE <: Exception] (
  val stream: EStream[T, EE]
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

  @inline def map[U](project: T => U): EStream[U, EE] = {
    stream.jsMap(project)
  }

  @inline def filter(passes: T => Boolean): EStream[T, EE] = {
    stream.jsFilter(passes)
  }

  @inline def filterByValue[T2 >: T](value: T2): EStream[T, EE] = {
    stream.jsFilter((v: T) => v == value)
  }

  @inline def fold[R](accumulate: (R, T) => R, seed: R): MemoryStream[R, EE] = {
    stream.jsFold(accumulate, seed)
  }

  // @TODO[Elegance] Simplify this – remove classTag, just check for js.Error

  // @TODO[Integrity] ExpErr should be just EE, but I can't get ClassTag on it because this is a value class
  @inline def replaceExpectedErrors[T2 >: T, ExpErr >: EE <: Exception : ClassTag](
    replace: ExpErr => XStream[T2]
  ): XStream[T2] = {
    val expectedErrorClass = implicitly[ClassTag[ExpErr]].runtimeClass
    stream.jsReplaceAllErrors((error: Exception | js.Error) => {
      if (expectedErrorClass.isInstance(error)) {
        replace(error.asInstanceOf[ExpErr])
      } else {
        XStream.throwUnexpectedError(error.asInstanceOf[js.Error])
      }
    })
  }

  // @TODO[API] EE can be just E, but `|` breaks variance
  @inline def replaceAllErrors[T2 >: T](
    replace: Exception | js.Error => XStream[T2]
  ): XStream[T2] = {
    stream.jsReplaceAllErrors(replace)
  }

  @inline def compose[T2, EE2 <: Exception, ResultStream <: EStream[T2, EE2]](
    operator: EStream[T, EE] => ResultStream
  ): ResultStream = {
    stream.jsCompose[T2, EE2, ResultStream]((thisStream: EStream[T, EE]) => operator(thisStream))
  }

  @JSName("debug")
  @inline def debugWithSpy(spy: T => Unit): EStream[T, EE] = {
    stream.jsDebugWithSpy(spy)
  }

  @inline def debugger(): EStream[T, EE] = {
    stream.jsDebugWithSpy((value: T) => js.debugger())
  }

  @inline def setDebugListener(listener: Listener[T, EE]): Unit = {
    stream.setDebugListener(listener)
  }
}
