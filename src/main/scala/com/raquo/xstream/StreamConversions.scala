package com.raquo.xstream

trait StreamConversions {

  implicit def toRichStream[T, EE <: Exception](
    stream: EStream[T, EE]
  ): RichStream[T, EE] = {
    new RichStream(stream)
  }

  implicit def toRichMemoryStream[T, EE <: Exception](
    memoryStream: MemoryStream[T, EE]
  ): RichMemoryStream[T, EE] = {
    new RichMemoryStream(memoryStream)
  }

  implicit def toMetaStream[T, EE <: Exception] (
    streamOfStreams: XStream[EStream[T, EE]]
  ): MetaStream[T, EE] = {
    new MetaStream(streamOfStreams)
  }

  implicit def toTupleStream2[T1, T2, EE <: Exception] (
    tupleStream: EStream[(T1, T2), EE]
  ): TupleStream2[T1, T2, EE] = {
    new TupleStream2(tupleStream)
  }

  implicit def toTupleStream3[T1, T2, T3, EE <: Exception] (
    tupleStream: EStream[(T1, T2, T3), EE]
  ): TupleStream3[T1, T2, T3, EE] = {
    new TupleStream3(tupleStream)
  }

  implicit def toTupleStream4[T1, T2, T3, T4, EE <: Exception] (
    tupleStream: EStream[(T1, T2, T3, T4), EE]
  ): TupleStream4[T1, T2, T3, T4, EE] = {
    new TupleStream4(tupleStream)
  }
}
