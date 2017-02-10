package com.raquo.xstream

import scala.scalajs.js

@js.native
trait Subscription[T, EE <: Exception] extends js.Object {

  private val _stream: EStream[T, EE] = js.native

  private val _listener: Listener[T, EE] = js.native

  def unsubscribe(): Unit = js.native
}
