package com.raquo.xstream

import scala.scalajs.js

object OptionalImplicits {

  implicit class ShamefulStream[+T, +E <: js.Error](val shamelessStream: XStream[T, E]) extends AnyVal {

    def shamefullySendNext[U >: T](value: U): Unit = shamelessStream.shamefullySendNext(value)

    def shamefullySendError[E2 >: E <: js.Error](error: E2): Unit = shamelessStream.shamefullySendError(error)

    def shamefullySendComplete(): Unit = shamelessStream.shamefullySendComplete()
  }
}
