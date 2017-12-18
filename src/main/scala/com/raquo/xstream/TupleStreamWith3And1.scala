package com.raquo.xstream

class TupleStreamWith3And1[+A, +B, +C, +D] (
  val tupleStream: XStream[((A, B, C), D)]
) extends AnyVal {

  @inline def map4[U](project: (A, B, C, D) => U): XStream[U] = {
    tupleStream.jsMap((tuple: ((A, B, C), D)) => project(tuple._1._1, tuple._1._2, tuple._1._3, tuple._2))
  }

  @inline def filter4(passes: (A, B, C, D) => Boolean): XStream[((A, B, C), D)] = {
    tupleStream.jsFilter((tuple: ((A, B, C), D)) => passes(tuple._1._1, tuple._1._2, tuple._1._3, tuple._2))
  }

  @inline def debugWithSpy4(spy: (A, B, C, D) => Any): XStream[((A, B, C), D)] = {
    tupleStream.jsDebugWithSpy((tuple: ((A, B, C), D)) => spy(tuple._1._1, tuple._1._2, tuple._1._3, tuple._2))
  }
}
