package com.raquo.xstream

import scala.scalajs.js
import scala.scalajs.js.|

object TupleOps {

  @inline def JSArrayToTuple2[A, B](arr: js.Array[A | B]): (A, B) = {
    (arr(0).asInstanceOf[A], arr(1).asInstanceOf[B])
  }

  @inline def JSArrayToTuple3[A, B, C](arr: js.Array[A | B | C]): (A, B, C) = {
    (arr(0).asInstanceOf[A], arr(1).asInstanceOf[B], arr(2).asInstanceOf[C])
  }

  @inline def JSArrayToTuple4[A, B, C, D](arr: js.Array[A | B | C | D]): (A, B, C, D) = {
    (arr(0).asInstanceOf[A], arr(1).asInstanceOf[B], arr(2).asInstanceOf[C], arr(3).asInstanceOf[D])
  }
}
