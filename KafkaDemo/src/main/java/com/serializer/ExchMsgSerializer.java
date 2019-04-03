package com.serializer;

import com.exchange.CBizPackage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Created by Administrator
 * 2019-04-03
 */

public class ExchMsgSerializer implements Serializer<CBizPackage> {
    public void configure(Map<String, ?> map, boolean b) {

    }

    public byte[] serialize(String s, CBizPackage msg) {
        ByteBuf buf = Unpooled.buffer(1024);
        try {
            msg.writeToBuf(buf);
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            return bytes;
        } finally {
            if (buf != null) {
                buf.release();
            }
        }
    }

    public void close() {

    }
}
