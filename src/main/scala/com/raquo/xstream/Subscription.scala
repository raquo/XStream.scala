package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@js.native
trait Subscription[T, EE <: Exception] extends js.Object {

  // @TODO[API] Is there a good reason to keep these private?
  // @TODO[API] Comparing subscriptions for example is much easier if these are public

  @JSName("_stream")
  val stream: EStream[T, EE] = js.native

  @JSName("_listener")
  val listener: Listener[T, EE] = js.native

  def unsubscribe(): Unit = js.native
}
