package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka.ticket;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEventKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class TicketKafkaPublisher {

    public static final Logger logger = LoggerFactory.getLogger(TicketKafkaPublisher.class);

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
        logger.debug("Transaction initialization [{}].",kafkaTemplate.inTransaction());
        kafkaTemplate.send(topic,key,value);
        logger.debug("Ticket Event [{}] has been sent to topic [{}].",key,topic);
    }

    /**
     * Publishes an events collection to a Kafka Topic
     * @param topic topic name
     * @param keys event key collection
     * @param values event value collection
     */
    public void publishAll(String topic, ArrayList<TicketGeneratedEventKey> keys, ArrayList<String> values) {
        logger.debug("Ready to send Events Collection to topic [{}].",topic);
        logger.debug("Transaction initialization [{}].",kafkaTemplate.inTransaction());

        // @see executeInTransaction
        //kafkaTemplate.executeInTransaction()


        for(int i = 0 ; i < keys.size() ; i ++) {
            kafkaTemplate.send(topic,keys.get(i),values.get(i));
        }
        logger.debug("Events collection has been sent to topic [{}].",topic);
    }

}
