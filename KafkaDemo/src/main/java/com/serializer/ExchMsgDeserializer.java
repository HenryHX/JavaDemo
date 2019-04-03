package com.serializer;

import com.exchange.CBizPackage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * Created by Administrator
 * 2019-04-03
 */

public class ExchMsgDeserializer implements Deserializer<CBizPackage> {
    public void configure(Map<String, ?> map, boolean b) {

    }

    public CBizPackage deserialize(String s, byte[] bytes) {
        CBizPackage msg = new CBizPackage();
        ByteBuf buf = Unpooled.copiedBuffer(bytes);

        try {
            boolean ret = msg.readFromBuf(buf);
            if (!ret) {
                throw new RuntimeException("unpack message from server error , can not construct bizPackage");
            }
        } finally {
            if (buf != null) {
                buf.release();
            }
        }

        return msg;
    }

    public void close() {

    }
}
