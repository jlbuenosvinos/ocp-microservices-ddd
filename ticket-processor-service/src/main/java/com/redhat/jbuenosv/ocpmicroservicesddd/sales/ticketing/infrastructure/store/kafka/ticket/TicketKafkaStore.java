package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka.ticket;

import com.google.common.eventbus.Subscribe;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.ActiveMQConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEventKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class TicketKafkaStore implements EventStore {

    public static final Logger logger = LoggerFactory.getLogger(TicketKafkaStore.class);

    @Autowired
    ActiveMQConfig mqConfig;

    //@Autowired
    //TicketKafkaPublisherConfig ticketKafkaPublisherConfig;

    /**
     * Saves a ticket event to a Kafka Topic
     * @param event ticket event
     */
    @Subscribe
    @Transactional("ticket-transaction-manager")
    public void store(TicketGeneratedEvent event) {
        logger.debug("store begin.");
        TicketKafkaPublisherConfig config = new TicketKafkaPublisherConfig();
        TicketGeneratedEventKey key = new TicketGeneratedEventKey(event.getTicket().getStoreId(),event.getTicket().getTicketId());
        config.publish(config.getKafkaTicketsTopicName(),key,event.getTicket().toJson());
        logger.debug("store end.");
    }

}
