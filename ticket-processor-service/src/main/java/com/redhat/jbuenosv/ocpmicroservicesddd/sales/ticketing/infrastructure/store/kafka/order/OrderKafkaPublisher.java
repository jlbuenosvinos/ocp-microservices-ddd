package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class OrderKafkaPublisher {

    public static final Logger logger = LoggerFactory.getLogger(OrderKafkaPublisher.class);

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * Publishes an event to a Kafka Topic
     * @param topic topic name
     * @param key order id key
     * @param value event value
     */
    public void publish(String topic, String key, String value) {
        logger.debug("Ready to send Event [{}] to topic [{}].",key,topic);
        logger.debug("Transaction initialization [{}].",kafkaTemplate.inTransaction());
        kafkaTemplate.send(topic,key,value);
        logger.debug("Order Event [{}] has been sent to topic [{}].",key,topic);
    }

}
