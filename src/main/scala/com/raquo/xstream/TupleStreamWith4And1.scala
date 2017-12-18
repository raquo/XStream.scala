package com.raquo.xstream

class TupleStreamWith4And1[+A, +B, +C, +D, +E] (
  val tupleStream: XStream[((A, B, C, D), E)]
) extends AnyVal {

  @inline def map5[U](project: (A, B, C, D, E) => U): XStream[U] = {
    tupleStream.jsMap((tuple: ((A, B, C, D), E)) => project(tuple._1._1, tuple._1._2, tuple._1._3, tuple._1._4, tuple._2))
  }

  @inline def filter5(passes: (A, B, C, D, E) => Boolean): XStream[((A, B, C, D), E)] = {
    tupleStream.jsFilter((tuple: ((A, B, C, D), E)) => passes(tuple._1._1, tuple._1._2, tuple._1._3, tuple._1._4, tuple._2))
  }

  @inline def debugWithSpy5(spy: (A, B, C, D, E) => Any): XStream[((A, B, C, D), E)] = {
    tupleStream.jsDebugWithSpy((tuple: ((A, B, C, D), E)) => spy(tuple._1._1, tuple._1._2, tuple._1._3, tuple._1._4, tuple._2))
  }
}
