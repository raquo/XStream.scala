package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

class RichMemoryStream[+T] (
  val memoryStream: MemoryStream[T]
) extends AnyVal {

  @inline def map[U](project: T => U): MemoryStream[U] = {
    memoryStream.jsMap(project)
  }

  @inline def replaceError[T2 >: T](
    replace: Exception | js.Error => XStream[T2]
  ): MemoryStream[T2] = {
    memoryStream.jsReplaceError(replace)
  }

  @JSName("debug")
  @inline def debugWithSpy(spy: T => Any): MemoryStream[T] = {
    memoryStream.jsDebugWithSpy(spy)
  }
}
