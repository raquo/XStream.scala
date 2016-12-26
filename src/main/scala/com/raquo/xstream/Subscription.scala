package com.raquo.xstream

import scala.scalajs.js

@js.native
trait Subscription[T] extends js.Object {

  private val _stream: XStream[T] = js.native

  private val _listener: Listener[T] = js.native

  def unsubscribe(): Unit = js.native
}
