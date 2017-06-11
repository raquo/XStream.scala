package com.raquo.xstream

import scala.scalajs.js.annotation.JSName

class TupleStream2[+T1, +T2] (
  val tupleStream: XStream[(T1, T2)]
) extends AnyVal {

  @inline def map[U](project: (T1, T2) => U): XStream[U] = {
    tupleStream.jsMap(project.tupled)
  }

  @inline def filter(passes: (T1, T2) => Boolean): XStream[(T1, T2)] = {
    tupleStream.jsFilter(passes.tupled)
  }

  @JSName("debug")
  @inline def debugWithSpy(spy: (T1, T2) => Any): XStream[(T1, T2)] = {
    tupleStream.jsDebugWithSpy(spy.tupled)
  }
}
