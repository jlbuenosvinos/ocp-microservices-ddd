package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEventKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class KafkaPublisher {

    public static final Logger logger = LoggerFactory.getLogger(KafkaPublisher.class);

    @Autowired
    private KafkaTemplate<TicketGeneratedEventKey,String> kafkaTemplate;

    /**
     * Publishes an event to a Kafka Topic
     * @param topic topic name
     * @param key event key
     * @param value event value
     */
    public void publish(String topic, TicketGeneratedEventKey key, String value) {
        logger.debug("Ready to send Event [{}] to topic [{}].",key,topic);
        kafkaTemplate.inTransaction();
        kafkaTemplate.send(topic,key,value);
        logger.debug("Event [{}] has been sent to topic [{}].",key,topic);
    }

}
