package com.ulknow.disruptor.demo1;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Administrator
 * 2020-04-24
 */

public class ValueEvent {
    private long value;
    public long getValue() {
        return value;
    }

    public void setValue(final long value) {
        this.value = value;
    }

    public static final EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>() {
        public ValueEvent newInstance() {
            return new ValueEvent();
        }
    };
}
