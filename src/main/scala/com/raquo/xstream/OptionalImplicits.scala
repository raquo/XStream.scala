package com.raquo.xstream

object OptionalImplicits {

  implicit class ShamefulStream[+T, +E](val shamelessStream: XStream[T, E]) extends AnyVal {

    def shamefullySendNext[U >: T](value: U): Unit = shamelessStream.shamefullySendNext(value)

    def shamefullySendError[E2 >: E](error: E2): Unit = shamelessStream.shamefullySendError(error)

    def shamefullySendComplete(): Unit = shamelessStream.shamefullySendComplete()
  }
}
