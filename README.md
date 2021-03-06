# XStream.scala

XStream.scala is a **Scala.js** interface to [XStream.js](https://github.com/staltz/xstream), an extremely intuitive, small, and fast functional reactive stream library for JavaScript.

XStream is notably used in [Cycle.js](https://github.com/cyclejs/cyclejs), an honestly functional and reactive JavaScript framework.

    "com.raquo.xstream" %%% "xstream" % "0.3.1"

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

Per XStream.js docs, you should normally avoid using `shamefullySend*` methods, so in order to use them you need to explicitly create an instance of `ShamefulStream`. It is a value class, so [normally](https://docs.scala-lang.org/overviews/core/value-classes.html) the instantiation does not actually happen at runtime, thus there is no runtime penalty to it.

You should not need to use `RawXStream` directly, use the `XStream` object instead.

## Caveats

In XStream.js/Cycle.js, a common convention is to name stream variables with trailing dollar signs, e.g. `response$` for a stream of responses. In Scala.js this naming sometimes causes a weird compilation error. I'm not sure why exactly, but I think it might have something to do with Scala's or Scala.js' internal representation making use of trailing dollar signs as well. I will try to figure this out some day, for now I personally prepend `$` instead, e.g. `$response` for a stream of responses. Although if you use jQuery maybe go for a `responseStream` instead to avoid the confusion.

## TODO

### Integrity

* Add tests (XStream has its own tests, but it we should verify this interface as well)
* I'm not convinced of `XStream` being actually covariant in `T`. `imitate` method seems to violate it, but its use case is also extremely limited to creating proxies. Find a compromise.

### Completeness

* Add interfaces for XStream extras
* `XStream.of` should accept multiple params

### Convenience

* Document basic usage and scala-specific things (e.g. tuple streams, shameful streams)
* Copy method documentation from original xstream
* Add more TupleStreamN types
* Add MemoryTupleStreamN convenience types

### Misc

* Update to latest version of XStream
* Figure out how versioning should work for this project (match XStream.js version?)

## Reporting Bugs

* If you found a bug, please open a Github issue describing actual and expected behavior.
* Please make sure that the bug is related to my Scala interface, not the underlying XStream.js library.

## My Related Projects

- [Scala DOM Types](https://github.com/raquo/scala-dom-types) – Type definitions that my projects use for all the HTML tags, attributes, properties, and styles
- [Scala DOM Builder](https://github.com/raquo/scala-dom-builder) – Low-level Scala & Scala.js library for building and manipulating DOM trees
- [Scala DOM TestUtils](https://github.com/raquo/scala-dom-testutils) – Test that your Javascript DOM nodes match your expectations
- [Laminar](https://github.com/raquo/laminar) – Reactive UI library based on _Scala DOM Builder_
- [Snabbdom.scala](https://github.com/raquo/Snabbdom.scala) – Scala.js interface to a popular JS virtual DOM library
- [Cycle.scala](https://github.com/raquo/Cycle.scala) – Scala.js interface to a popular JS functional reactive library

## Author

Nikita Gazarov – [raquo.com](http://raquo.com)

## License and Credits

XStream.scala is provided under the [MIT license](https://github.com/raquo/XStream.scala/blob/master/LICENSE.md).

XStream.scala is a Scala.js interface to [XStream.js](https://github.com/staltz/xstream), which we use under the terms of its [MIT license](https://github.com/staltz/xstream/blob/master/LICENSE).
