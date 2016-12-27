package com.raquo.xstream

import scala.scalajs.js

@js.native
trait Subscription[T, E <: js.Error] extends js.Object {

  private val _stream: XStream[T, E] = js.native

  private val _listener: Listener[T, E] = js.native

  def unsubscribe(): Unit = js.native
}
