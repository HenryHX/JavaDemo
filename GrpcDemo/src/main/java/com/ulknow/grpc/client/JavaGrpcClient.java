package com.ulknow.grpc.client;

import com.google.protobuf.TextFormat;
import com.ulknow.grpc.examples.GreeterGrpc;
import com.ulknow.grpc.examples.GrpcEntity;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;

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

    //请求流是异步调用，普通的是同步调用，我们在普通的方法里创建的实例是同步的，
    //所以我们要在 JavaGrpcClient 中新加一个 异步调用的方法，添加一个异步的实例
    public <Result> Result runAsync(Functional<GreeterGrpc.GreeterStub, Result> functional) {
        //TestServiceGrpc.newStub 返回的是一个异步的实例
        GreeterGrpc.GreeterStub greeterStub = GreeterGrpc.newStub(channel);
        return functional.run(greeterStub);
    }

    private Channel channel() {
        return ManagedChannelBuilder.forAddress("127.0.0.1", 29999).usePlaintext().build();
    }

    public static void testNormal() {
        GrpcEntity.HelloRequest helloRequest = GrpcEntity.HelloRequest.newBuilder().setName("lililili").build();

        JavaGrpcClient client = new JavaGrpcClient();
        GrpcEntity.HelloReply helloReply = client.run(greeterBlockingStub -> greeterBlockingStub.sayHello(helloRequest));

        System.out.println(TextFormat.printToUnicodeString(helloReply));
    }

    /**
     * 客户端可以源源不断的给服务端传参数，服务端会源源不断的接受服务端的参数，最后在客户端完成请求的时候，服务端返回一个结果
     */
    public static void testReqStream() {
        GrpcEntity.HelloRequest helloRequest = GrpcEntity.HelloRequest.newBuilder().setName("lililili").build();

        //实现一个返回流观察者
        //供server回复消息时候使用
        //这个方法只是请求流调用，在grpc的内部 最后返回的时候 只返回第一个指定的返回值，不管返回了多少个，在客户端那边只会收到 第一个返回的结果
        StreamObserver<GrpcEntity.HelloReply> responseObserver = new StreamObserver<GrpcEntity.HelloReply>() {
            @Override
            public void onNext(GrpcEntity.HelloReply helloReply) {
                System.out.print("client receive response, detail:" + TextFormat.printToUnicodeString(helloReply));
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };

        JavaGrpcClient client = new JavaGrpcClient();
        //将我们实现的 返回流观察者 传进去，返回给我们一个 请求流观察者
        //其实这里返回的 请求流观察者 就是服务端那里返回给我们的那个实现，服务端那里 返回流观察者 是我们实现的 传给它的
        StreamObserver<GrpcEntity.HelloRequest> requestObserver =
                client.runAsync(greeterStub -> greeterStub.sayHelloReqStream(responseObserver));

        //利用Server返回的 请求流观察者， 发送流式请求，直至结束标志
        requestObserver.onNext(helloRequest);
        requestObserver.onNext(helloRequest);
        requestObserver.onNext(helloRequest);
        requestObserver.onCompleted();

        try {
            Thread.sleep(600000);
        }
        catch (Exception ex){}
    }

    /**
     * 和请求流接口完全相反，请求流是异步，响应流是同步，请求流是接受多个请求返回一个结果，响应流是接受一个请求返回多个结果
     */
    public static void testRepStream() {
        GrpcEntity.HelloRequest helloRequest = GrpcEntity.HelloRequest.newBuilder().setName("lililili").build();

        JavaGrpcClient client = new JavaGrpcClient();
        //返回流请求是同步的，所以要调同步的方法，返回了一个迭代器
        Iterator<GrpcEntity.HelloReply> helloReplyIterator =
                client.run(greeterBlockingStub -> greeterBlockingStub.sayHelloRepStream(helloRequest));

        //由于是同步调用，在forEach中会等待服务端的每一个返回结果
        helloReplyIterator.forEachRemaining(helloReply -> {
            System.out.println(TextFormat.printToUnicodeString(helloReply));
        });
    }

    /**
     * 双向流的服务端和请求流的没啥区别，只是在接收到请求的时候没有立刻结束请求
     */
    public static void testReqRepStream() {
        GrpcEntity.HelloRequest helloRequest = GrpcEntity.HelloRequest.newBuilder().setName("lililili").build();

        //实现一个返回流观察者
        //供server回复消息时候使用
        StreamObserver<GrpcEntity.HelloReply> responseObserver = new StreamObserver<GrpcEntity.HelloReply>() {
            @Override
            public void onNext(GrpcEntity.HelloReply helloReply) {
                System.out.print("client receive response, detail:" + TextFormat.printToUnicodeString(helloReply));
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        };

        JavaGrpcClient client = new JavaGrpcClient();
        //将我们实现的 返回流观察者 传进去，返回给我们一个 请求流观察者
        //其实这里返回的 请求流观察者 就是服务端那里返回给我们的那个实现，服务端那里 返回流观察者 是我们实现的 传给它的
        StreamObserver<GrpcEntity.HelloRequest> requestObserver =
                client.runAsync(greeterStub -> greeterStub.sayHelloReqRepStream(responseObserver));

        //利用Server返回的 请求流观察者， 发送流式请求，直至结束标志
        requestObserver.onNext(helloRequest);
        requestObserver.onNext(helloRequest);
        requestObserver.onNext(helloRequest);
        requestObserver.onCompleted();

        try {
            Thread.sleep(600000);
        }
        catch (Exception ex){}
    }

    public static void main(String[] args) {
//        testNormal();
//        testReqStream();
//        testRepStream();
        testReqRepStream();
    }
}
