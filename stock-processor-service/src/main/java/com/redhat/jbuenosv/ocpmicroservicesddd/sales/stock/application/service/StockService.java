package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;

/**
 * This service includes the main logic for the ticketing projection service
 * Created by jlbuenosvinos.
 */
public interface StockService {

    /**
     * Projects a ticket
     * @param ticket ticket to be processed
     */
    void processTicket(Ticket ticket);

}
