package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;

/**
 * This service includes the main logic for the ticketing system.
 * Created by jlbuenosvinos.
 */
public interface TicketService {

    /**
     * Process an order
     * @param order Physical order
     */
    void processOrder(Order order);

}
