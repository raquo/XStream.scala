package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@js.native
trait MemoryStream[+T, +E] extends XStream[T, E] {

  @JSName("map")
  override def mapJs[U](project: js.Function1[T, U]): MemoryStream[U, E] = js.native

  override def mapTo[U](projectedValue: U): MemoryStream[U, E] = js.native

  override def take(amount: Int): MemoryStream[T, E] = js.native

  override def endWhen(other: XStream[_, _]): MemoryStream[T, E] = js.native

  @JSName("replaceError")
  override def replaceErrorJs[U >: T, E2](
    replace: js.Function1[E, XStream[U, E2]]
  ): MemoryStream[T, E2] = js.native

  @JSName("debug")
  override def debugJs(spy: js.Function1[T, Any]): MemoryStream[T, E] = js.native

  override def debug(label: String): MemoryStream[T, E] = js.native

  override def debug(): MemoryStream[T, E] = js.native
}
