package com.raquo.xstream

class TupleStream3[+A, +B, +C] (
  val tupleStream: XStream[(A, B, C)]
) extends AnyVal {

  @inline def map3[U](project: (A, B, C) => U): XStream[U] = {
    tupleStream.jsMap(project.tupled)
  }

  @inline def filter3(passes: (A, B, C) => Boolean): XStream[(A, B, C)] = {
    tupleStream.jsFilter(passes.tupled)
  }

  @inline def debugWithSpy3(spy: (A, B, C) => Any): XStream[(A, B, C)] = {
    tupleStream.jsDebugWithSpy(spy.tupled)
  }
}
