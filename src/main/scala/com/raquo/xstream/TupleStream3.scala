package com.raquo.xstream

import scala.scalajs.js.annotation.JSName

class TupleStream3[+T1, +T2, +T3] (
  val tupleStream: XStream[(T1, T2, T3)]
) extends AnyVal {

  @inline def map[U](project: (T1, T2, T3) => U): XStream[U] = {
    tupleStream.jsMap(project.tupled)
  }

  @inline def filter(passes: (T1, T2, T3) => Boolean): XStream[(T1, T2, T3)] = {
    tupleStream.jsFilter(passes.tupled)
  }

  @JSName("debug")
  @inline def debugWithSpy(spy: (T1, T2, T3) => Any): XStream[(T1, T2, T3)] = {
    tupleStream.jsDebugWithSpy(spy.tupled)
  }
}
