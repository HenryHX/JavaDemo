package com.henry.nats;

import io.nats.client.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class PubSubTest {

    public Connection connection;
    public String subject = "order/sh/6002";

    @Before
    public void init() throws IOException, InterruptedException {
        connection = Nats.connect("nats://*.*.*.*:4222");
    }

    @Test
    public void testPub() {
        connection.publish(subject, "hello nats".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void testSubSync() throws InterruptedException {
        Subscription subscribe = connection.subscribe(subject);
        Message message = subscribe.nextMessage(Duration.ofMinutes(1));
        String response = new String(message.getData(), StandardCharsets.UTF_8);
        System.out.println("收到同步消息："+ response);
    }

    @Test
    public void testSubAsync() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Dispatcher dispatcher = connection.createDispatcher(message -> {
            String response = new String(message.getData(), StandardCharsets.UTF_8);
            System.out.println("收到异步消息：" + response);
        });

        dispatcher.subscribe(subject);

        latch.await();
    }
}
