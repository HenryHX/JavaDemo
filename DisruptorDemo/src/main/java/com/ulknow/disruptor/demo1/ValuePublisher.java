package com.ulknow.disruptor.demo1;

import com.lmax.disruptor.RingBuffer;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator
 * 2020-04-24
 */

/**
 * 多个生产者和一个消费者之间进行数据传递，和一对一不同的是，涉及到生产者ValuePublisher定义。
 * 和单生产者不同的时，需要让多个生产者同时工作，并且每个生产者处理其中的某个区间，
 * 在本例子中将分为2个区间，2个生产者每个发布各自区间中的数据
 */
public final class ValuePublisher implements Runnable {
    /**
     * CyclicBarrier确保两个生产者同时生产数据，每个生产者处理[start, end)中数据的发布。
     */
    private final CyclicBarrier cyclicBarrier;
    private final RingBuffer<ValueEvent> ringBuffer;
    private final long start;
    private final long end;

    public ValuePublisher(
            final CyclicBarrier cyclicBarrier,
            final RingBuffer<ValueEvent> ringBuffer,
            final long start,
            final long end) {
        this.cyclicBarrier = cyclicBarrier;
        this.ringBuffer = ringBuffer;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try {
            cyclicBarrier.await();
            for (long i = start; i < end; i++) {
                long sequence = ringBuffer.next();
                ValueEvent event = ringBuffer.get(sequence);
                event.setValue(i);
                ringBuffer.publish(sequence);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}