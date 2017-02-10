package com.raquo.xstream

class TupleStream4[+T1, +T2, +T3, +T4, +EE <: Exception] (
  val tupleStream: XStream[(T1, T2, T3, T4), EE]
) extends AnyVal {

  @inline def map[U](project: (T1, T2, T3, T4) => U): XStream[U, EE] = {
    tupleStream.jsMap(project.tupled)
  }

  @inline def filter(passes: (T1, T2, T3, T4) => Boolean): XStream[(T1, T2, T3, T4), EE] = {
    tupleStream.filterJs(passes.tupled)
  }

  @inline def debug(spy: (T1, T2, T3, T4) => Any): XStream[(T1, T2, T3, T4), EE] = {
    tupleStream.debugJs(spy.tupled)
  }
}
