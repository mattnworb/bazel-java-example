# bazel-java-example

This repo is an example of using Bazel and Java. A bunch of example code for
gRPC servers and command-line clients are adapter from
<https://github.com/grpc/grpc-java> just to be able to demonstrate building
`java_binary`s that use Protobuf, grpc-java, etc.

## Examples

### hello-world

In one terminal:

```console
bazel run //hello-world/java:server -- --port 50051
```

which should output:

```console
Nov 17, 2025 1:06:42 PM com.mattnworb.helloworld.HelloWorldServer start
INFO: Server started, listening on 50051
```

and then in another terminal:

```console
bazel run //hello-world/java:cli -- --target=localhost:50051 Matt
```

which should output:

```console
sending: name: "Matt"
received: message: "Hello, Matt!"
```

In this example, the Java source files for both targets lives alongside each
other, with `java_binary` rules defined in `hello-world/java/BUILD.bazel` that
very narrowly includes a single file for each target.

### route-guide

These are lifted from
<https://github.com/grpc/grpc-java/tree/v1.75.0/examples/src/main/java/io/grpc/examples/routeguide>,
with the code moved around into a bit of a different structure than above, to
demonstrate having `src/main/java` directories nested under each "module" /
java_binary:

```
routeguide
├── client
│   ├── BUILD.bazel
│   └── src
│       └── main
│           └── java
├── proto
│   └── mattnworb
│       └── routeguide
│           └── v1
├── server
│   ├── BUILD.bazel
│   └── src
│       └── main
│           └── java
└── util
    ├── BUILD.bazel
    └── src
        └── main
            ├── java
            └── resources
```

This example has a gRPC server and a command-line client similar to
`//helloworld`. To run, in one terminal run `bazel run //routeguide/server` and
in another terminal run `bazel run //routeguide/client`.



## Tools

This repo uses <https://github.com/buildbuddy-io/bazel_env.bzl> to make it
easier to run some tools distributed via the Bazel setup in this repo. Run
`bazel run //tools:bazel_env` and then follow the instructions that it outputs,
and install and configure [direnv](https://direnv.net/).

Once you set, when your shell's current directory is in this repo, you can run:

- `format` to format code with <https://github.com/aspect-build/rules_lint/>,
  using google-java-format for Java
- `buildifier` to run buildifier on BUILD files
- `java` to run the Java toolchain that Bazel in this repo is set up with
