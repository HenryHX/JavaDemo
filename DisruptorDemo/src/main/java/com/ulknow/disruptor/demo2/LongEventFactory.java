package com.ulknow.disruptor.demo2;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Administrator
 * 2020-04-24
 */

public class LongEventFactory implements EventFactory<LongEvent> {
    public LongEvent newInstance() {
        return new LongEvent();
    }
}