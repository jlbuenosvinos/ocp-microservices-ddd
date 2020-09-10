package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import org.springframework.stereotype.Service;

/**
 * Created by jlbuenosvinos.
 */
@Service
public class OrderTicketsProcessManagerImpl implements OrderTicketsProcessManager {

    /**
     * Process the tickets of an Order
     * @param order Physical order
     */
    @Override
    public void processOrderTickets(Order order) {

    }


}
