package com.henry.nats;

import io.nats.client.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class ReqRepTest {

    public Connection connection;
    public String subject = "order/sh/6002";
    public String replySubject = NUID.nextGlobal();

    @Before
    public void init() throws IOException, InterruptedException {
        connection = Nats.connect("nats://*.*.*.*:4222");
    }

    @Test
    public void testReqASync() throws InterruptedException, ExecutionException {
        CountDownLatch latch = new CountDownLatch(1);

        CompletableFuture<Message> request =
                connection.request(subject, "hello nats, this is a message for response".getBytes(StandardCharsets.UTF_8));

        System.out.println(new String(request.get().getData(), StandardCharsets.UTF_8));

        latch.await();
    }

    @Test
    public void testReqSync() throws InterruptedException, ExecutionException {
        CountDownLatch latch = new CountDownLatch(1);

        Message request =
                connection.request(subject, "hello nats, this is a message for response".getBytes(StandardCharsets.UTF_8), Duration.ofMinutes(1));

        System.out.println(new String(request.getData(), StandardCharsets.UTF_8));

        latch.await();
    }

    @Test
    public void testSubAsync() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Dispatcher dispatcher = connection.createDispatcher(message -> {
            String response = new String(message.getData(), StandardCharsets.UTF_8);
            System.out.println("收到异步消息：" + response);
            connection.publish(message.getReplyTo(), "this is reply".getBytes(StandardCharsets.UTF_8));
        });

        dispatcher.subscribe(subject);

        latch.await();
    }

    @Test
    public void testSubAsync2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Dispatcher dispatcher = connection.createDispatcher(message -> {
            String response = new String(message.getData(), StandardCharsets.UTF_8);
            System.out.println("收到异步消息：" + response);
            connection.publish(message.getReplyTo(), "this is reply2".getBytes(StandardCharsets.UTF_8));
        });

        dispatcher.subscribe(subject);

        latch.await();
    }
}
