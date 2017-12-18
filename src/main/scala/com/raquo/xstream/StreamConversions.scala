package com.raquo.xstream

trait StreamConversions {

  implicit def toExtras[T](
    stream: XStream[T]
  ): Extras[T] = {
    new Extras(stream)
  }

  implicit def toRichStream[T](
    stream: XStream[T]
  ): RichStream[T] = {
    new RichStream(stream)
  }

  implicit def toRichMemoryStream[T](
    memoryStream: MemoryStream[T]
  ): RichMemoryStream[T] = {
    new RichMemoryStream(memoryStream)
  }

  implicit def toMetaStream[T] (
    streamOfStreams: XStream[XStream[T]]
  ): MetaStream[T] = {
    new MetaStream(streamOfStreams)
  }

  implicit def toTupleStream2[T1, T2] (
    tupleStream: XStream[(T1, T2)]
  ): TupleStream2[T1, T2] = {
    new TupleStream2(tupleStream)
  }

  implicit def toTupleStream3[T1, T2, T3] (
    tupleStream: XStream[(T1, T2, T3)]
  ): TupleStream3[T1, T2, T3] = {
    new TupleStream3(tupleStream)
  }

  implicit def toTupleStream4[T1, T2, T3, T4] (
    tupleStream: XStream[(T1, T2, T3, T4)]
  ): TupleStream4[T1, T2, T3, T4] = {
    new TupleStream4(tupleStream)
  }

  implicit def toTupleStreamWith2And1[A, B, C] (
    tupleStream: XStream[((A, B), C)]
  ): TupleStreamWith2And1[A, B, C] = {
    new TupleStreamWith2And1(tupleStream)
  }

  implicit def toTupleStreamWith3And1[A, B, C, D] (
    tupleStream: XStream[((A, B, C), D)]
  ): TupleStreamWith3And1[A, B, C, D] = {
    new TupleStreamWith3And1(tupleStream)
  }

  implicit def toTupleStreamWith4And1[A, B, C, D, E] (
    tupleStream: XStream[((A, B, C, D), E)]
  ): TupleStreamWith4And1[A, B, C, D, E] = {
    new TupleStreamWith4And1(tupleStream)
  }
}
