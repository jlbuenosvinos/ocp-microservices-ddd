package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;

/**
 * Created by jlbuenosvinos.
 */
public interface OrderProcessManager {

    /**
     * Process an order
     * @param order Physical order
     */
    void processOrder(Order order);

}
