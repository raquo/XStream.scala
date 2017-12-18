package com.raquo.xstream

class TupleStream4[+A, +B, +C, +D] (
  val tupleStream: XStream[(A, B, C, D)]
) extends AnyVal {

  @inline def map4[U](project: (A, B, C, D) => U): XStream[U] = {
    tupleStream.jsMap(project.tupled)
  }

  @inline def filter4(passes: (A, B, C, D) => Boolean): XStream[(A, B, C, D)] = {
    tupleStream.jsFilter(passes.tupled)
  }

  @inline def debugWithSpy4(spy: (A, B, C, D) => Any): XStream[(A, B, C, D)] = {
    tupleStream.jsDebugWithSpy(spy.tupled)
  }
}
