package com.mattnworb.helloworld;

import com.google.protobuf.TextFormat;
import com.mattnworb.helloworld.v1.HelloWorldServiceGrpc;
import com.mattnworb.helloworld.v1.Service.SayHelloRequest;
import com.mattnworb.helloworld.v1.Service.SayHelloResponse;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class HelloWorldCli {
  public static void main(String[] args) {

    ArgumentParser parser =
        ArgumentParsers.newFor("HelloWorldCli")
            .build()
            .defaultHelp(true)
            .description("Command-line client for HelloWorld gRPC service.");

    parser.addArgument("--target").setDefault("localhost:50051").help("The address to connect to.");

    parser.addArgument("name").help("The name to send to the server.");

    Namespace ns = null;
    try {
      ns = parser.parseArgs(args);
    } catch (ArgumentParserException e) {
      parser.handleError(e);
      System.exit(1);
      return;
    }

    String target = ns.getString("target");
    String name = ns.getString("name");

    ManagedChannel channel =
        Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();

    SayHelloRequest request = SayHelloRequest.newBuilder().setName(name).build();

    HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub =
        HelloWorldServiceGrpc.newBlockingStub(channel);

    System.out.printf("sending: %s%n", TextFormat.shortDebugString(request));

    SayHelloResponse response = stub.sayHello(request);
    System.out.printf("received: %s%n", TextFormat.shortDebugString(response));
  }
}
