package com.henry.nats;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.NUID;
import io.nats.client.Nats;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public class QueueTest {

    public Connection connection;
    public String subject = "order/sh/6002";
    public String replySubject = "order/sh/6002/rep";
    public String queueName = "queue";
    public String queueName2 = "queue2";


    @Before
    public void init() throws IOException, InterruptedException {
        connection = Nats.connect("nats://*.*.*.*:4222");
    }

    @Test
    public void testReq() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Dispatcher dispatcher = connection.createDispatcher(message -> {
            String response = new String(message.getData(), StandardCharsets.UTF_8);
            System.out.println("收到reply消息：" + response);
        });
        dispatcher.subscribe(replySubject);
//        dispatcher.unsubscribe(replySubject, 4);

        connection.publish(subject, replySubject, "hello nats1, this is a message for response".getBytes(StandardCharsets.UTF_8));
        connection.publish(subject, replySubject, "hello nats2, this is a message for response".getBytes(StandardCharsets.UTF_8));
        connection.publish(subject, replySubject, "hello nats3, this is a message for response".getBytes(StandardCharsets.UTF_8));
        connection.publish(subject, replySubject, "hello nats4, this is a message for response".getBytes(StandardCharsets.UTF_8));


        latch.await();
    }

    @Test
    public void testSubAsync() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Dispatcher dispatcher = connection.createDispatcher(message -> {
            String response = new String(message.getData(), StandardCharsets.UTF_8);
            System.out.println("收到异步消息：" + response);
            connection.publish(message.getReplyTo(), ("this is reply：" + response).getBytes(StandardCharsets.UTF_8));
        });

        dispatcher.subscribe(subject, queueName);

        latch.await();
    }

    @Test
    public void testSubAsync2() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Dispatcher dispatcher = connection.createDispatcher(message -> {
            String response = new String(message.getData(), StandardCharsets.UTF_8);
            System.out.println("收到异步消息：" + response);
            connection.publish(message.getReplyTo(), ("this is reply2：" + response).getBytes(StandardCharsets.UTF_8));
        });

        dispatcher.subscribe(subject, queueName);

        latch.await();
    }

    @Test
    public void testSubAsync3() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Dispatcher dispatcher = connection.createDispatcher(message -> {
            String response = new String(message.getData(), StandardCharsets.UTF_8);
            System.out.println("收到异步消息：" + response);
            connection.publish(message.getReplyTo(), ("this is reply3：" + response).getBytes(StandardCharsets.UTF_8));
        });

        dispatcher.subscribe(subject, queueName2);

        latch.await();
    }
}
