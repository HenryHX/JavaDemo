package com.listener;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.support.ProducerListener;

/**
 * Created by Administrator
 * 2019-04-03
 */

public class ExchMsgProducerListener implements ProducerListener {
    protected final Logger logger = LogManager.getLogger(ExchMsgProducerListener.class);

    public void onSuccess(String topic, Integer partition, Object key, Object value, RecordMetadata recordMetadata) {
        logger.debug("kafka 发送消息成功 key {} topic {} " , key , topic);
    }

    public void onError(String topic, Integer partition, Object key, Object value, Exception exception) {
        logger.error("kafka 发送消息失败 key {} topic {} " , key , topic , exception);
    }

    public boolean isInterestedInSuccess() {
        return false;
    }
}
