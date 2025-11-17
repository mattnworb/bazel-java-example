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
