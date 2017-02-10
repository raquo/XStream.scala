package com.raquo.xstream

class TupleStream3[+T1, +T2, +T3, +EE <: Exception] (
  val tupleStream: XStream[(T1, T2, T3), EE]
) extends AnyVal {

  @inline def map[U](project: (T1, T2, T3) => U): XStream[U, EE] = {
    tupleStream.jsMap(project.tupled)
  }

  @inline def filter(passes: (T1, T2, T3) => Boolean): XStream[(T1, T2, T3), EE] = {
    tupleStream.filterJs(passes.tupled)
  }

  @inline def debug(spy: (T1, T2, T3) => Any): XStream[(T1, T2, T3), EE] = {
    tupleStream.debugJs(spy.tupled)
  }
}
