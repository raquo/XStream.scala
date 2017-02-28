package com.raquo.xstream

class ShamefulStream[T, EE <: Exception](val stream: EStream[T, EE]) extends AnyVal {

  def shamefullySendNext(value: T): Unit = {
    stream.shamefullySendNext(value)
  }

  def shamefullySendError(error: EE): Unit = {
    stream.shamefullySendError(error)
  }

  def shamefullySendComplete(): Unit = {
    stream.shamefullySendComplete()
  }
}
