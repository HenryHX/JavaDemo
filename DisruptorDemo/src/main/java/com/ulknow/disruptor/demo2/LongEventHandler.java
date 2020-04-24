package com.ulknow.disruptor.demo2;

import com.lmax.disruptor.EventHandler;

/**
 * Created by Administrator
 * 2020-04-24
 */

public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Event: " + event);
    }
}
