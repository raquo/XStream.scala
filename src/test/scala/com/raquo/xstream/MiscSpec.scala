package com.raquo.xstream

class MiscSpec extends UnitSpec {

  it("collect should work") {

    trait BaseFoo { val foo: String = "basefoo" }
    trait Foo extends BaseFoo { override val foo: String = "foo" }
    trait SubFoo extends Foo { override val foo: String = "subfoo" }

    val foo = new Foo {}
    val baseFoo = new BaseFoo {}

    val producer = Producer[BaseFoo](
      onStart = listener => {
        listener.next(foo)
        listener.next(baseFoo)
      },
      onStop = () => fail("Producer should not have stopped")
    )

    var count = 0
    val stream = XStream
      .create(producer)
      .collect { case f: Foo => f }
    stream.addListener(Listener(
      onNext = value => {
        value shouldBe foo
        count += 1
      },
      onError = err => fail(s"onError should have fired: $err"),
      onComplete = () => fail(s"onComplete should have fired")
    ))
    count shouldBe 1
  }

}
