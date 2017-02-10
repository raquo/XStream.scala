package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

@js.native
trait MemoryStream[+T, +EE <: Exception] extends EStream[T, EE] {

  @JSName("map")
  override def jsMap[U](project: js.Function1[T, U]): MemoryStream[U, EE] = js.native

  override def mapTo[U](projectedValue: U): MemoryStream[U, EE] = js.native

  override def take(amount: Int): MemoryStream[T, EE] = js.native

  override def endWhen(other: EStream[_, _]): MemoryStream[T, EE] = js.native

  @JSName("replaceError")
  override def jsReplaceAllErrors[U >: T](
    replace: js.Function1[Exception | js.Error, XStream[U]]
  ): XMemoryStream[U] = js.native

  @JSName("debug")
  override def debugJs(spy: js.Function1[T, Any]): MemoryStream[T, EE] = js.native

  override def debug(label: String): MemoryStream[T, EE] = js.native

  override def debug(): MemoryStream[T, EE] = js.native
}
