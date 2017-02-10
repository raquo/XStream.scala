package com.raquo.xstream

class TupleStream2[+T1, +T2, +EE <: Exception] (
  val tupleStream: EStream[(T1, T2), EE]
) extends AnyVal {

  @inline def map[U](project: (T1, T2) => U): EStream[U, EE] = {
    tupleStream.jsMap(project.tupled)
  }

  @inline def filter(passes: (T1, T2) => Boolean): EStream[(T1, T2), EE] = {
    tupleStream.filterJs(passes.tupled)
  }

  @inline def debug(spy: (T1, T2) => Any): EStream[(T1, T2), EE] = {
    tupleStream.debugJs(spy.tupled)
  }
}
