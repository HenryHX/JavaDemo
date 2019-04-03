package com.listener;

import com.alibaba.fastjson.JSON;
import com.exchange.CBizPackage;
import com.exchange.TopicConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.Map;

/**
 * Created by Administrator
 * 2019-04-03
 */

public class ExchMsgConsumerListener implements AcknowledgingMessageListener<String, CBizPackage>, ConsumerSeekAware {

    private Logger logger = LogManager.getLogger(ExchMsgConsumerListener.class);


    public void onMessage(ConsumerRecord<String, CBizPackage> record, Acknowledgment acknowledgment) {
        logger.debug("========================接收到kafka消息========================");
        logger.debug("消息主题：{}", record.topic());
        logger.debug("消息业务ID：{}", record.value().getHeader().gettID());
        logger.debug(JSON.toJSON(record.value()));

        if (acknowledgment != null) {
            logger.debug("提交offset");
            acknowledgment.acknowledge();
        }
    }

    public void registerSeekCallback(ConsumerSeekCallback callback) {

    }

    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        for (Map.Entry<TopicPartition, Long> topicPartitionLongEntry : assignments.entrySet()) {
            if (topicPartitionLongEntry.getKey().topic().equals(TopicConstant.Kafka_MarketData)) {
                callback.seekToEnd(topicPartitionLongEntry.getKey().topic(), topicPartitionLongEntry.getKey().partition());
            }
        }
    }

    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {

    }
}
