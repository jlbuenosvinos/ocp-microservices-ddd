package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store;

import com.google.common.eventbus.Subscribe;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.ActiveMQConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;

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

    /**
     * Saves a ticket event to a Kafka Topic
     * @param event ticket event
     */
    @Subscribe
    public void store(TicketGeneratedEvent event) {
        logger.debug("Saving begin.");

        TicketGeneratedEvent ticketGeneratedEvent = event;







    }

}