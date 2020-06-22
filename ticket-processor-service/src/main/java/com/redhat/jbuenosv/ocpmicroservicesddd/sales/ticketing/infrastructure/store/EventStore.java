package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;

/**
 * Created by jlbuenosvinos.
 */
public interface EventStore {

    /**
     * Saves a Ticket Event
     * @param event Ticket event to be store
     */
    void store(TicketGeneratedEvent event);

}
