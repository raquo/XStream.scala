package com.raquo.xstream

import scala.scalajs.js.annotation.JSName

class TupleStream2[+T1, +T2, +EE <: Exception] (
  val tupleStream: EStream[(T1, T2), EE]
) extends AnyVal {

  @inline def map[U](project: (T1, T2) => U): EStream[U, EE] = {
    tupleStream.jsMap(project.tupled)
  }

  @inline def filter(passes: (T1, T2) => Boolean): EStream[(T1, T2), EE] = {
    tupleStream.jsFilter(passes.tupled)
  }

  @JSName("debug")
  @inline def debugWithSpy(spy: (T1, T2) => Any): EStream[(T1, T2), EE] = {
    tupleStream.jsDebugWithSpy(spy.tupled)
  }
}
