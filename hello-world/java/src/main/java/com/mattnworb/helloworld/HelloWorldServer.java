package com.mattnworb.helloworld;

import com.mattnworb.helloworld.v1.HelloWorldServiceGrpc;
import com.mattnworb.helloworld.v1.Service.SayHelloRequest;
import com.mattnworb.helloworld.v1.Service.SayHelloResponse;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

// inspired by
// https://github.com/grpc/grpc-java/blob/a57c373973f01524cde6d02161d87e77f5b638d4/examples/src/main/java/io/grpc/examples/helloworld/HelloWorldServer.java
public class HelloWorldServer {
  private static final Logger logger = Logger.getLogger(HelloWorldServer.class.getName());

  private Server server;

  private void start(int port) throws IOException {
    /*
     * By default gRPC uses a global, shared Executor.newCachedThreadPool() for gRPC callbacks into
     * your application. This is convenient, but can cause an excessive number of threads to be
     * created if there are many RPCs. It is often better to limit the number of threads your
     * application uses for processing and let RPCs queue when the CPU is saturated.
     * The appropriate number of threads varies heavily between applications.
     * Async application code generally does not need more threads than CPU cores.
     */
    ExecutorService executor = Executors.newFixedThreadPool(2);
    server =
        Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
            .executor(executor)
            .addService(new ServiceImpl())
            .build()
            .start();
    logger.info("Server started, listening on " + port);
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread() {
              @Override
              public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                  HelloWorldServer.this.stop();
                } catch (InterruptedException e) {
                  if (server != null) {
                    server.shutdownNow();
                  }
                  e.printStackTrace(System.err);
                } finally {
                  executor.shutdown();
                }
                System.err.println("*** server shut down");
              }
            });
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  /** Await termination on the main thread since the grpc library uses daemon threads. */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  /** Main launches the server from the command line. */
  public static void main(String[] args) throws IOException, InterruptedException {
    ArgumentParser parser =
        ArgumentParsers.newFor("HelloWorldServer")
            .build()
            .defaultHelp(true)
            .description("Server for HelloWorld gRPC service.");

    parser
        .addArgument("--port")
        .type(Integer.class)
        .setDefault(50051)
        .help("The port on which the server should run.");

    Namespace ns = null;
    try {
      ns = parser.parseArgs(args);
    } catch (ArgumentParserException e) {
      parser.handleError(e);
      System.exit(1);
      return;
    }

    final int port = ns.getInt("port");

    final HelloWorldServer server = new HelloWorldServer();
    server.start(port);
    server.blockUntilShutdown();
  }

  static class ServiceImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {
    public void sayHello(
        SayHelloRequest request, StreamObserver<SayHelloResponse> responseObserver) {

      String msg = String.format("Hello, %s!", request.getName());

      responseObserver.onNext(SayHelloResponse.newBuilder().setMessage(msg).build());
      responseObserver.onCompleted();
    }
  }
}
