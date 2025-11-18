# bazel-java-example

An example of using protobuf and grpc-java in Bazel.

## To run

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

## Tools

This repo uses <https://github.com/buildbuddy-io/bazel_env.bzl> to make it
easier to run some tools distributed via the Bazel setup in this repo. Run
`bazel run //tools:bazel_env` and then follow the instructions that it outputs, and install and configure [direnv](https://direnv.net/).

Once you set, when your shell's current directory is in this repo, you can run:

- `format` to format code with <https://github.com/aspect-build/rules_lint/>,
  using google-java-format for Java
- `buildifier` to run buildifier on BUILD files
- `java` to run the Java toolchain that Bazel in this repo is set up with
