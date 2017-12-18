package com.raquo.xstream.rawextras

import com.raquo.xstream.XStream

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@js.native
@JSImport("xstream/extra/sampleCombine", JSImport.Default)
object RawSampleCombine extends js.Object {

  def apply[A, B](streams: XStream[B]*): js.Function1[XStream[A], XStream[js.Array[A | B]]] = js.native // @TODO does this do what I think it does?

  def apply[A, B, C](
    stream1: XStream[B],
    stream2: XStream[C]
  ): js.Function1[XStream[A], XStream[js.Array[A | B | C]]] = js.native

  def apply[A, B, C, D](
    stream1: XStream[B],
    stream2: XStream[C],
    stream3: XStream[D]
  ): js.Function1[XStream[A], XStream[js.Array[A | B | C | D]]] = js.native

  def apply[A, B, C, D, E](
    stream1: XStream[B],
    stream2: XStream[C],
    stream3: XStream[D],
    stream4: XStream[E]
  ): js.Function1[XStream[A], XStream[js.Array[A | B | C | D | E]]] = js.native

}
