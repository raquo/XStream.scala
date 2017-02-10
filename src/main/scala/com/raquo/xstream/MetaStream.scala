package com.raquo.xstream

class MetaStream[T, EE <: Exception] (
  val streamOfStreams: XStream[EStream[T, EE]]
) extends AnyVal {

  @inline def flatten: EStream[T, EE] = {
    streamOfStreams.jsFlatten[T, EE]()
  }
}
