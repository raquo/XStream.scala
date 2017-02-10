package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.|

class RichMemoryStream[+T, +EE <: Exception] (
  val memoryStream: MemoryStream[T, EE]
) extends AnyVal {

  @inline def map[U](project: T => U): MemoryStream[U, EE] = {
    memoryStream.jsMap(project)
  }

  @inline def replaceAllErrors[T2 >: T](
    replace: Exception | js.Error => XStream[T2, Nothing]
  ): MemoryStream[T2, Nothing] = {
    memoryStream.jsReplaceAllErrors(replace)
  }

  @inline def debug(spy: T => Any): MemoryStream[T, EE] = {
    memoryStream.debugJs(spy)
  }
}
