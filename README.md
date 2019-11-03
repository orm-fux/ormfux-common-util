## Description

A collection of utilities. There are, for example, "every day" utilities for operating on 
collections, working with nullable values, or date values. Some of the more rarely used 
ones are the reflection utilities for class and property analysis and invocations.

Available on [MavenCentral](https://search.maven.org/search?q=g:com.github.orm-fux%20AND%20a:ormfux-common-util).

## Dependency Declaration

Maven dependency:

```xml
<dependency>
  <groupId>com.github.orm-fux</groupId>
  <artifactId>ormfux-common-util</artifactId>
  <version>1.6.1</version>
</dependency>
```

Gradle dependency:

```
compile group: 'com.github.orm-fux', name: 'ormfux-common-util', version: '1.6.1'
```

## Version History

**1.6.1**

Extended `Stream` API:

* Create Stream from `Map`.
* Create Stream from `java.util.stream.Stream`.
* `forEach` is not a terminal operation anymore.
* Added terminal operation that filters and directly creates a `Map` - i.e. combination of `filter` and `map` operations.

