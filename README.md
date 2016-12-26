# XStream.scala

XStream.scala is a **Scala.js** interface to [XStream.js](https://github.com/staltz/xstream), _an extremely intuitive, small, and fast functional reactive stream library for JavaScript_.

XStream is notably used in [Cycle.js](https://github.com/cyclejs/cyclejs), an honestly functional and reactive JavaScript framework. Yes, I'm working on the Cycle.scala interface as well.

## Documentation

For now, see [XStream.js docs](https://github.com/staltz/xstream/blob/master/README.md). XStream.scala retains original XStream.js classes and method names with only a few deviations.

Notably, class `Stream` is named `XStream` in my interface to avoid the inconvenience of sharing the name with `scala.collection.immutable.Stream`.

When mapping over a stream of tuples, you can use `map` methods from `TupleStream<N>` implicit classes to avoid writing `case` statements.

Per XStream.js docs, you should normally avoid using `shamefullySend*` methods, so in order to use them you need to explicitly import `OptionalImplicits.scala`.

You should not need to use any XStream traits starting with `Raw` in your code (there's only one right now).

## Features
* Lightweight, type-safe wrapper over XStream.js – native Scala types, no extraneous allocations at runtime.
* Convenient implicit conversions to work with streams of tuples and streams of streams.

## Caveats

In XStream.js/Cycle.js, a common convention is to name stream variables with trailing dollar signs, e.g. `response$` for a stream of responses. In Scala.js this naming sometimes causes a weird compilation error. I'm not sure why exactly, but I think it might have something to do with Scala's or Scala.js' internal representation making use of trailing dollar signs as well. I will try to figure this out some day, for now I personally prepend `$` instead, e.g. `$response`. Although if you use jQuery maybe go for a `responseStream` instead to avoid the confusion.

## TODO

### Integrity
* Streams should be of `[T, E]` (error), not just of `[T]`
* Resolve all `@TODO` comments in the code
* Add tests (XStream has its own tests, but it would be nice to verify this interface as well)

### Completeness
* Add interfaces for XStream extras
* `XStream.of` should accept multiple params

### Convenience
* Document basic usage and scala-specific things (e.g. tuple streams, shameful streams)
* Copy method documentation from original xstream
* Add more TupleStreamN types
* Add MemoryTupleStreamN convenience types

### Performance
* Examine generated JavaScript code: measure the file size, see what we can improve

### Misc
* Update to latest version of XStream
* Figure out how versioning should work for this project
* Figure out why we can't always use trailing dollar sign in stream variable names

## Reporting bugs
* If you found a bug, please open a github issue describing actual and expected behavior.
* Please make sure that the bug is not on the TODO list above, and that it's related to my scala interface, not the underlying XStream.js library.

## License

[MIT license](https://github.com/raquo/xstream-scala/blob/master/LICENSE.md)

## Author

Nikita Gazarov

## Credits

XStream.scala is a Scala.js interface to André Staltz's [XStream.js](https://github.com/staltz/xstream), which we use under the terms of their [MIT license](https://github.com/staltz/xstream/blob/master/LICENSE).
