package com.mattnworb.helloworld;

import com.google.protobuf.TextFormat;
import com.mattnworb.helloworld.v1.HelloWorldServiceGrpc;
import com.mattnworb.helloworld.v1.Service.SayHelloRequest;
import com.mattnworb.helloworld.v1.Service.SayHelloResponse;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;

public class HelloWorldCli {
  public static void main(String[] args) {
    String target = "localhost:50051";
    ManagedChannel channel =
        Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();

    SayHelloRequest request = SayHelloRequest.newBuilder().setName("Joe").build();

    HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub =
        HelloWorldServiceGrpc.newBlockingStub(channel);

    System.out.printf("sending: %s%n", TextFormat.shortDebugString(request));

    SayHelloResponse response = stub.sayHello(request);
    System.out.printf("received: %s%n", TextFormat.shortDebugString(response));
  }
}
