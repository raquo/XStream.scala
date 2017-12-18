package com.raquo.xstream.rawextras

import com.raquo.xstream.XStream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("xstream/extra/delay", JSImport.Default)
object RawDelay extends js.Object {

  def apply[A](period: Int): js.Function1[XStream[A], XStream[A]] = js.native
}
