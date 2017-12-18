package com.raquo.xstream

class TupleStream2[+A, +B] (
  val tupleStream: XStream[(A, B)]
) extends AnyVal {

  @inline def map2[U](project: (A, B) => U): XStream[U] = {
    tupleStream.jsMap(project.tupled)
  }

  @inline def filter2(passes: (A, B) => Boolean): XStream[(A, B)] = {
    tupleStream.jsFilter(passes.tupled)
  }

  @inline def debugWithSpy2(spy: (A, B) => Any): XStream[(A, B)] = {
    tupleStream.jsDebugWithSpy(spy.tupled)
  }
}
