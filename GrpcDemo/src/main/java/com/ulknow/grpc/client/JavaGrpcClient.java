package com.ulknow.grpc.client;

import com.google.protobuf.TextFormat;
import com.ulknow.grpc.examples.GreeterGrpc;
import com.ulknow.grpc.examples.GrpcEntity;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by Administrator
 * 2019-10-10
 */

public class JavaGrpcClient {
    private Channel channel = channel();

    public <Result> Result run(Functional<GreeterGrpc.GreeterBlockingStub, Result> functional) {
        GreeterGrpc.GreeterBlockingStub greeterBlockingStub = GreeterGrpc.newBlockingStub(channel);
        return functional.run(greeterBlockingStub);
    }

    private Channel channel() {
        return ManagedChannelBuilder.forAddress("127.0.0.1", 29999).usePlaintext().build();
    }

    public static void main(String[] args) {
        GrpcEntity.HelloRequest helloRequest = GrpcEntity.HelloRequest.newBuilder().setName("lililili").build();

        JavaGrpcClient client = new JavaGrpcClient();
        GrpcEntity.HelloReply helloReply = client.run(greeterBlockingStub -> greeterBlockingStub.sayHello(helloRequest));

        System.out.println(TextFormat.printToUnicodeString(helloReply));
    }
}
