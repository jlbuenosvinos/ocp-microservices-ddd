package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka;

import com.google.common.eventbus.Subscribe;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.ActiveMQConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEventKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class KafkaTicketStore implements EventStore {

    public static final Logger logger = LoggerFactory.getLogger(KafkaTicketStore.class);

    @Autowired
    ActiveMQConfig mqConfig;

    @Autowired
    KafkaPublisherConfig kafkaPublisherConfig;

    /**
     * Saves a ticket event to a Kafka Topic
     * @param event ticket event
     */
    @Subscribe
    public void store(TicketGeneratedEvent event) {
        logger.debug("store begin.");
        TicketGeneratedEventKey key = new TicketGeneratedEventKey(event.getTicket().getStoreId(),event.getTicket().getTicketId());
        kafkaPublisherConfig.publisher().publish(kafkaPublisherConfig.getKafkaTicketsTopicName(),key,event);
        logger.debug("store end.");
    }

}
