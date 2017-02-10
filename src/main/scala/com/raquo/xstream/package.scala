package com.raquo

package object xstream extends StreamConversions {

  /** A [[EStream]] with no expected errors */
  type XStream[+T] = EStream[T, Nothing]

  /** A [[MemoryStream]] with no expected errors */
  type XMemoryStream[+T] = MemoryStream[T, Nothing]
}
