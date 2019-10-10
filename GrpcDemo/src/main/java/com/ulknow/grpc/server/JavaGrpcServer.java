package com.ulknow.grpc.server;

import com.google.protobuf.TextFormat;
import com.ulknow.grpc.examples.GreeterGrpc;
import com.ulknow.grpc.examples.GrpcEntity;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;


/**
 * Created by Administrator
 * 2019-10-10
 */

public class JavaGrpcServer extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(GrpcEntity.HelloRequest request, StreamObserver<GrpcEntity.HelloReply> responseObserver) {
        System.out.println("server receive request, detail:" + TextFormat.printToUnicodeString(request));

        GrpcEntity.HelloReply helloReply = GrpcEntity.HelloReply.newBuilder().setMessage("thx from 测试:" + request.getName()).build();
        responseObserver.onNext(helloReply);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<GrpcEntity.HelloRequest> sayHelloReqStream(StreamObserver<GrpcEntity.HelloReply> responseObserver) {
        return new StreamObserver<GrpcEntity.HelloRequest>() {
            @Override
            public void onNext(GrpcEntity.HelloRequest helloRequest) {
                System.out.print("server receive stream request, detail:" + TextFormat.printToUnicodeString(helloRequest));
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                GrpcEntity.HelloReply helloReply = GrpcEntity.HelloReply.newBuilder().setMessage("处理完了所有的流req").build();
                responseObserver.onNext(helloReply);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void sayHelloRepStream(GrpcEntity.HelloRequest request, StreamObserver<GrpcEntity.HelloReply> responseObserver) {
        System.out.print("server receive request, detail:" + TextFormat.printToUnicodeString(request));

        GrpcEntity.HelloReply helloReply = GrpcEntity.HelloReply.newBuilder().setMessage("thx from 测试:" + request.getName()).build();

        responseObserver.onNext(helloReply);
        responseObserver.onNext(helloReply);
        responseObserver.onNext(helloReply);
        try {
            Thread.sleep(2000);
        }
        catch (Exception ex){}
        responseObserver.onNext(helloReply);

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<GrpcEntity.HelloRequest> sayHelloReqRepStream(StreamObserver<GrpcEntity.HelloReply> responseObserver) {
        return new StreamObserver<GrpcEntity.HelloRequest>() {
            @Override
            public void onNext(GrpcEntity.HelloRequest helloRequest) {
                System.out.print("server receive stream request, detail:" + TextFormat.printToUnicodeString(helloRequest));

                GrpcEntity.HelloReply helloReply = GrpcEntity.HelloReply.newBuilder().setMessage("thx from 测试:" + helloRequest.getName()).build();
                responseObserver.onNext(helloReply);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerBuilder.forPort(29999).addService(new JavaGrpcServer()).build().start();
        while (true) {
            Thread.sleep(1000);
        }
    }
}
