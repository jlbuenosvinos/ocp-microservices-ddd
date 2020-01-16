package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEventKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

    public static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<TicketGeneratedEventKey,TicketGeneratedEvent> kafkaTemplate;

    /**
     * Sends an event to a Kafka Topic
     * @param topic topic name
     * @param key event key
     * @param value event value
     */
    public void send(String topic, TicketGeneratedEventKey key, TicketGeneratedEvent value) {
        logger.debug("Ready to send Event [{}] to topic [{}].",key,topic);
        kafkaTemplate.send(topic,key,value);
        logger.debug("Event [{}] has been sent to topic [{}].",key,topic);
    }

}
