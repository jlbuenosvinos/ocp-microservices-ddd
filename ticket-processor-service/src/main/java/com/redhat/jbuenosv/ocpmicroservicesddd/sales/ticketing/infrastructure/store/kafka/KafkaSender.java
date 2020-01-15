package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

    public static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends an event to a Kafka Topic
     * @param topic topic name
     * @param payload payload of the event
     */
    public void send(String topic, String payload) {
        kafkaTemplate.send(topic, payload);
        System.out.println("Event [" + payload + "] has been sent to topic [" + topic + "].");
    }

}
