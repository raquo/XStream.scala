package com.raquo.xstream

class MetaStream[T] (
  val streamOfStreams: XStream[XStream[T]]
) extends AnyVal {

  @inline def flatten: XStream[T] = {
    streamOfStreams.jsFlatten[T]()
  }
}
