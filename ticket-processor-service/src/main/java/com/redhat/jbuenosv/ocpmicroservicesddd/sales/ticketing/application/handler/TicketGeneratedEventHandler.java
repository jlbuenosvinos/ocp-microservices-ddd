package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.handler;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.TicketGeneratedEventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class TicketGeneratedEventHandler implements EventHandler {

    public static final Logger logger = LoggerFactory.getLogger(TicketGeneratedEventHandler.class);

    @Autowired
    TicketGeneratedEventStore ticketStore;

    @Override
    public <T> void handle(final T event) {
        TicketGeneratedEvent ticketGeneratedEvent = (TicketGeneratedEvent)event;
        logger.debug("Event [{}] has to be handled.",ticketGeneratedEvent.getEventId());
        ticketStore.save(event);
        logger.debug("Event [{}] handled.",ticketGeneratedEvent.getEventId());
    }

}
