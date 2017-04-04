# XStream.scala

XStream.scala is a **Scala.js** interface to [XStream.js](https://github.com/staltz/xstream), an extremely intuitive, small, and fast functional reactive stream library for JavaScript.

XStream is notably used in [Cycle.js](https://github.com/cyclejs/cyclejs), an honestly functional and reactive JavaScript framework.

## Features

* Lightweight, type-safe wrapper over XStream.js – native Scala types making use of value classes to avoid extraneous allocations at runtime.
* Convenient extension methods to work with streams of tuples and streams of streams.

## Why XStream?

It's simple and lightweight. It makes the most sense when the developer does not typically `subscribe` to streams manually. See these posts from the creator of XStream.js:

* [Why we actually built XStream](http://staltz.com/why-we-actually-built-xstream.html)
* [Why we built XStream](http://staltz.com/why-we-built-xstream.html)
* [Cold and hot callbacks](http://staltz.com/cold-and-hot-callbacks.html)

## Documentation

See [XStream.js docs](https://github.com/staltz/xstream/blob/master/README.md). XStream.scala retains original XStream.js classes and method names with only a few deviations that are easily discoverable with code autocompletion or (less conveniently) by looking in the code.

Notably, class `Stream` is named `XStream` in my interface to avoid the inconvenience of sharing the name with `scala.collection.immutable.Stream`.

When mapping over a stream of tuples, you can use `map` methods from `TupleStream<N>` implicit classes to avoid the need for boilerplate or partial functions.

Per XStream.js docs, you should normally avoid using `shamefullySend*` methods, so in order to use them you need to explicitly create an instance of `ShamefulStream`.

You should not need to use `RawXStream` directly, use the `XStream` object instead.

## Error Handling

XStream.scala notably deviates from XStream.js by providing an optional way to declare what type of errors a stream is expected to produce, if any.

In Javascript with dynamic typing this is not an issue any more than the rest of your code being dynamically typed, but in Scala it is valuable to differentiate error values from exceptions.
 
We provide an `EStream[Value, ExpectedError]` class to help solve this. The `XStream{Value]` type that you normally use is just `EStream[Value, Nothing]`.

I'm not quite sure that this is a good idea. Initially this was added to support my Scala interface to Cycle.js due to how its HTTP driver works, but using `onError` to send back an error value seems like an FRP equivalent of using exceptions for business logic – something to be avoided. I have abandoned my Cycle.scala effort so maybe I'll remove this "feature" eventually.

## Caveats

In XStream.js/Cycle.js, a common convention is to name stream variables with trailing dollar signs, e.g. `response$` for a stream of responses. In Scala.js this naming sometimes causes a weird compilation error. I'm not sure why exactly, but I think it might have something to do with Scala's or Scala.js' internal representation making use of trailing dollar signs as well. I will try to figure this out some day, for now I personally prepend `$` instead, e.g. `$response` for a stream of responses. Although if you use jQuery maybe go for a `responseStream` instead to avoid the confusion.

## TODO

### Usability

* Publish to MavenCentral (Soon!) (use `sbt publishLocal` for now)

### Integrity

* Add tests (XStream has its own tests, but it we should verify this interface as well)
* I'm not convinced of `XStream` being actually covariant in `T`. `imitate` method seems to violate it, but its use case is also extremely limited to creating proxies. Find a compromise.

### Completeness

* Add interfaces for XStream extras
* `XStream.of` should accept multiple params

### Convenience

* Document basic usage and scala-specific things (e.g. tuple streams, shameful streams)
* Document EStream error tracking and caveats
* Copy method documentation from original xstream
* Add more TupleStreamN types
* Add MemoryTupleStreamN convenience types

### Misc

* Update to latest version of XStream
* Figure out how versioning should work for this project (match XStream.js version?)

## Reporting bugs

* If you found a bug, please open a Github issue describing actual and expected behavior.
* Please make sure that the bug is related to my Scala interface, not the underlying XStream.js library.

## License

[MIT license](https://github.com/raquo/xstream-scala/blob/master/LICENSE.md)

## Author

Nikita Gazarov – [raquo.com](http://raquo.com)

## License and Credits

XStream.scala is provided under the MIT license.

XStream.scala is a Scala.js interface to André Staltz's [XStream.js](https://github.com/staltz/xstream), which we use under the terms of their [MIT license](https://github.com/staltz/xstream/blob/master/LICENSE).
