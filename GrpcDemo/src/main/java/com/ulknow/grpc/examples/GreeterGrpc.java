package com.ulknow.grpc.examples;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.*;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.*;

/**
 * <pre>
 * The greeter service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.24.0)",
    comments = "Source: message.proto")
public final class GreeterGrpc {

  private GreeterGrpc() {}

  public static final String SERVICE_NAME = "helloworld.Greeter";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<GrpcEntity.HelloRequest,
      GrpcEntity.HelloReply> getSayHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SayHello",
      requestType = GrpcEntity.HelloRequest.class,
      responseType = GrpcEntity.HelloReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<GrpcEntity.HelloRequest,
      GrpcEntity.HelloReply> getSayHelloMethod() {
    io.grpc.MethodDescriptor<GrpcEntity.HelloRequest, GrpcEntity.HelloReply> getSayHelloMethod;
    if ((getSayHelloMethod = GreeterGrpc.getSayHelloMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getSayHelloMethod = GreeterGrpc.getSayHelloMethod) == null) {
          GreeterGrpc.getSayHelloMethod = getSayHelloMethod =
              io.grpc.MethodDescriptor.<GrpcEntity.HelloRequest, GrpcEntity.HelloReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SayHello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcEntity.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcEntity.HelloReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("SayHello"))
              .build();
        }
      }
    }
    return getSayHelloMethod;
  }

  private static volatile io.grpc.MethodDescriptor<GrpcEntity.HelloRequest,
      GrpcEntity.HelloReply> getSayHelloReqStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SayHelloReqStream",
      requestType = GrpcEntity.HelloRequest.class,
      responseType = GrpcEntity.HelloReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<GrpcEntity.HelloRequest,
      GrpcEntity.HelloReply> getSayHelloReqStreamMethod() {
    io.grpc.MethodDescriptor<GrpcEntity.HelloRequest, GrpcEntity.HelloReply> getSayHelloReqStreamMethod;
    if ((getSayHelloReqStreamMethod = GreeterGrpc.getSayHelloReqStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getSayHelloReqStreamMethod = GreeterGrpc.getSayHelloReqStreamMethod) == null) {
          GreeterGrpc.getSayHelloReqStreamMethod = getSayHelloReqStreamMethod =
              io.grpc.MethodDescriptor.<GrpcEntity.HelloRequest, GrpcEntity.HelloReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SayHelloReqStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcEntity.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcEntity.HelloReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("SayHelloReqStream"))
              .build();
        }
      }
    }
    return getSayHelloReqStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<GrpcEntity.HelloRequest,
      GrpcEntity.HelloReply> getSayHelloRepStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SayHelloRepStream",
      requestType = GrpcEntity.HelloRequest.class,
      responseType = GrpcEntity.HelloReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<GrpcEntity.HelloRequest,
      GrpcEntity.HelloReply> getSayHelloRepStreamMethod() {
    io.grpc.MethodDescriptor<GrpcEntity.HelloRequest, GrpcEntity.HelloReply> getSayHelloRepStreamMethod;
    if ((getSayHelloRepStreamMethod = GreeterGrpc.getSayHelloRepStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getSayHelloRepStreamMethod = GreeterGrpc.getSayHelloRepStreamMethod) == null) {
          GreeterGrpc.getSayHelloRepStreamMethod = getSayHelloRepStreamMethod =
              io.grpc.MethodDescriptor.<GrpcEntity.HelloRequest, GrpcEntity.HelloReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SayHelloRepStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcEntity.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcEntity.HelloReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("SayHelloRepStream"))
              .build();
        }
      }
    }
    return getSayHelloRepStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<GrpcEntity.HelloRequest,
      GrpcEntity.HelloReply> getSayHelloReqRepStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SayHelloReqRepStream",
      requestType = GrpcEntity.HelloRequest.class,
      responseType = GrpcEntity.HelloReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<GrpcEntity.HelloRequest,
      GrpcEntity.HelloReply> getSayHelloReqRepStreamMethod() {
    io.grpc.MethodDescriptor<GrpcEntity.HelloRequest, GrpcEntity.HelloReply> getSayHelloReqRepStreamMethod;
    if ((getSayHelloReqRepStreamMethod = GreeterGrpc.getSayHelloReqRepStreamMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getSayHelloReqRepStreamMethod = GreeterGrpc.getSayHelloReqRepStreamMethod) == null) {
          GreeterGrpc.getSayHelloReqRepStreamMethod = getSayHelloReqRepStreamMethod =
              io.grpc.MethodDescriptor.<GrpcEntity.HelloRequest, GrpcEntity.HelloReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SayHelloReqRepStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcEntity.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  GrpcEntity.HelloReply.getDefaultInstance()))
              .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("SayHelloReqRepStream"))
              .build();
        }
      }
    }
    return getSayHelloReqRepStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GreeterStub newStub(io.grpc.Channel channel) {
    return new GreeterStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GreeterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new GreeterBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GreeterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new GreeterFutureStub(channel);
  }

  /**
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static abstract class GreeterImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sayHello(GrpcEntity.HelloRequest request,
                         io.grpc.stub.StreamObserver<GrpcEntity.HelloReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSayHelloMethod(), responseObserver);
    }

    /**
     * <pre>
     *请求流
     * </pre>
     */
    public io.grpc.stub.StreamObserver<GrpcEntity.HelloRequest> sayHelloReqStream(
        io.grpc.stub.StreamObserver<GrpcEntity.HelloReply> responseObserver) {
      return asyncUnimplementedStreamingCall(getSayHelloReqStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     *响应流
     * </pre>
     */
    public void sayHelloRepStream(GrpcEntity.HelloRequest request,
                                  io.grpc.stub.StreamObserver<GrpcEntity.HelloReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSayHelloRepStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     *双向流接口
     * </pre>
     */
    public io.grpc.stub.StreamObserver<GrpcEntity.HelloRequest> sayHelloReqRepStream(
        io.grpc.stub.StreamObserver<GrpcEntity.HelloReply> responseObserver) {
      return asyncUnimplementedStreamingCall(getSayHelloReqRepStreamMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSayHelloMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                GrpcEntity.HelloRequest,
                GrpcEntity.HelloReply>(
                  this, METHODID_SAY_HELLO)))
          .addMethod(
            getSayHelloReqStreamMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                GrpcEntity.HelloRequest,
                GrpcEntity.HelloReply>(
                  this, METHODID_SAY_HELLO_REQ_STREAM)))
          .addMethod(
            getSayHelloRepStreamMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                GrpcEntity.HelloRequest,
                GrpcEntity.HelloReply>(
                  this, METHODID_SAY_HELLO_REP_STREAM)))
          .addMethod(
            getSayHelloReqRepStreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                GrpcEntity.HelloRequest,
                GrpcEntity.HelloReply>(
                  this, METHODID_SAY_HELLO_REQ_REP_STREAM)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static final class GreeterStub extends io.grpc.stub.AbstractStub<GreeterStub> {
    private GreeterStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GreeterStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void sayHello(GrpcEntity.HelloRequest request,
                         io.grpc.stub.StreamObserver<GrpcEntity.HelloReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSayHelloMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *请求流
     * </pre>
     */
    public io.grpc.stub.StreamObserver<GrpcEntity.HelloRequest> sayHelloReqStream(
        io.grpc.stub.StreamObserver<GrpcEntity.HelloReply> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getSayHelloReqStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     *响应流
     * </pre>
     */
    public void sayHelloRepStream(GrpcEntity.HelloRequest request,
                                  io.grpc.stub.StreamObserver<GrpcEntity.HelloReply> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSayHelloRepStreamMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *双向流接口
     * </pre>
     */
    public io.grpc.stub.StreamObserver<GrpcEntity.HelloRequest> sayHelloReqRepStream(
        io.grpc.stub.StreamObserver<GrpcEntity.HelloReply> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getSayHelloReqRepStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static final class GreeterBlockingStub extends io.grpc.stub.AbstractStub<GreeterBlockingStub> {
    private GreeterBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GreeterBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public GrpcEntity.HelloReply sayHello(GrpcEntity.HelloRequest request) {
      return blockingUnaryCall(
          getChannel(), getSayHelloMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *响应流
     * </pre>
     */
    public java.util.Iterator<GrpcEntity.HelloReply> sayHelloRepStream(
        GrpcEntity.HelloRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getSayHelloRepStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeter service definition.
   * </pre>
   */
  public static final class GreeterFutureStub extends io.grpc.stub.AbstractStub<GreeterFutureStub> {
    private GreeterFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GreeterFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<GrpcEntity.HelloReply> sayHello(
        GrpcEntity.HelloRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSayHelloMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAY_HELLO = 0;
  private static final int METHODID_SAY_HELLO_REP_STREAM = 1;
  private static final int METHODID_SAY_HELLO_REQ_STREAM = 2;
  private static final int METHODID_SAY_HELLO_REQ_REP_STREAM = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GreeterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GreeterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAY_HELLO:
          serviceImpl.sayHello((GrpcEntity.HelloRequest) request,
              (io.grpc.stub.StreamObserver<GrpcEntity.HelloReply>) responseObserver);
          break;
        case METHODID_SAY_HELLO_REP_STREAM:
          serviceImpl.sayHelloRepStream((GrpcEntity.HelloRequest) request,
              (io.grpc.stub.StreamObserver<GrpcEntity.HelloReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAY_HELLO_REQ_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sayHelloReqStream(
              (io.grpc.stub.StreamObserver<GrpcEntity.HelloReply>) responseObserver);
        case METHODID_SAY_HELLO_REQ_REP_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sayHelloReqRepStream(
              (io.grpc.stub.StreamObserver<GrpcEntity.HelloReply>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GreeterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GreeterBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return GrpcEntity.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Greeter");
    }
  }

  private static final class GreeterFileDescriptorSupplier
      extends GreeterBaseDescriptorSupplier {
    GreeterFileDescriptorSupplier() {}
  }

  private static final class GreeterMethodDescriptorSupplier
      extends GreeterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GreeterMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GreeterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GreeterFileDescriptorSupplier())
              .addMethod(getSayHelloMethod())
              .addMethod(getSayHelloReqStreamMethod())
              .addMethod(getSayHelloRepStreamMethod())
              .addMethod(getSayHelloReqRepStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
