package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;

/**
 * Created by jlbuenosvinos.
 */
public interface OrderTicketsProcessManager {

    /**
     * Process the tickets of an Order
     * @param order Physical order
     */
    void processOrderTickets(Order order);

}
