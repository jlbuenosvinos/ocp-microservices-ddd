package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;

/**
 * Created by jlbuenosvinos.
 */
public interface TicketProcessManager {

    /**
     * Process the ticket event
     * @param ticket ticket
     */
    void processTicket(Ticket ticket);

}
