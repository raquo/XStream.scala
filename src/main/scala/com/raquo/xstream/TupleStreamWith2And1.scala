package com.raquo.xstream

class TupleStreamWith2And1[+A, +B, +C] (
  val tupleStream: XStream[((A, B), C)]
) extends AnyVal {

  @inline def map3[U](project: (A, B, C) => U): XStream[U] = {
    tupleStream.jsMap((tuple: ((A, B), C)) => project(tuple._1._1, tuple._1._2, tuple._2))
  }

  @inline def filter3(passes: (A, B, C) => Boolean): XStream[((A, B), C)] = {
    tupleStream.jsFilter((tuple: ((A, B), C)) => passes(tuple._1._1, tuple._1._2, tuple._2))
  }

  @inline def debugWithSpy3(spy: (A, B, C) => Any): XStream[((A, B), C)] = {
    tupleStream.jsDebugWithSpy((tuple: ((A, B), C)) => spy(tuple._1._1, tuple._1._2, tuple._2))
  }
}
