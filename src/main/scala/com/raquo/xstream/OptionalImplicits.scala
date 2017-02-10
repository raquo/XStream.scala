package com.raquo.xstream

object OptionalImplicits {

  // @TODO[API] this should not be implicit!

  implicit class ShamefulStream[T, EE <: Exception](val shamelessStream: EStream[T, EE]) extends AnyVal {

    def shamefullySendNext(value: T): Unit = {
      shamelessStream.shamefullySendNext(value)
    }

    def shamefullySendError(error: EE): Unit = {
      shamelessStream.shamefullySendError(error)
    }

    def shamefullySendComplete(): Unit = {
      shamelessStream.shamefullySendComplete()
    }
  }
}
