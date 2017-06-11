package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

@js.native
trait MemoryStream[+T] extends XStream[T] {

  @JSName("map")
  override def jsMap[U](project: js.Function1[T, U]): MemoryStream[U] = js.native

  override def mapTo[U](projectedValue: U): MemoryStream[U] = js.native

  override def take(amount: Int): MemoryStream[T] = js.native

  override def endWhen(other: XStream[_]): MemoryStream[T] = js.native

  @JSName("replaceError")
  override def jsReplaceError[U >: T](
    replace: js.Function1[Exception | js.Error, XStream[U]]
  ): MemoryStream[U] = js.native

  @JSName("debug")
  override def jsDebugWithSpy(spy: js.Function1[T, Any]): MemoryStream[T] = js.native

  @JSName("debug")
  override def debugWithLabel(label: String): MemoryStream[T] = js.native

  override def debug(): MemoryStream[T] = js.native
}
