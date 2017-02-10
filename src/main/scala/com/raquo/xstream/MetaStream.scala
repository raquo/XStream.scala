package com.raquo.xstream

class MetaStream[T, EE <: Exception] (
  val streamOfStreams: XStream[XStream[T, EE], Nothing]
) extends AnyVal {

  @inline def flatten: XStream[T, EE] = {
    streamOfStreams.jsFlatten[T, EE]()
  }
}
