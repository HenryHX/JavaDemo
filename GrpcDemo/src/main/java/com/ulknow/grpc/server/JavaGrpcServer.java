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

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerBuilder.forPort(29999).addService(new JavaGrpcServer()).build().start();
        while (true) {
            Thread.sleep(1000);
        }
    }
}
