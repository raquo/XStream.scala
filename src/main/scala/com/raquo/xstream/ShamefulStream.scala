package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.|

class ShamefulStream[T](val stream: XStream[T]) extends AnyVal {

  def shamefullySendNext(value: T): Unit = {
    stream.shamefullySendNext(value)
  }

  def shamefullySendError(error: Exception | js.Error): Unit = {
    stream.shamefullySendError(error)
  }

  def shamefullySendComplete(): Unit = {
    stream.shamefullySendComplete()
  }
}
