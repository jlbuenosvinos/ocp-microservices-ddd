package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command.NewOrderSubmittedCommand;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command.NewOrderCommandHandler;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessManagerImpl {

    @Autowired
    NewOrderCommandHandler newOrderCommandHandler;

    @Autowired
    NewOrderSubmittedCommand newOrderSubmittedCommand;

    /**
     * Process an order
     * @param order Physical order
     */
    public void processOrder(Order order) {
        newOrderSubmittedCommand.setOrder(order);
        newOrderCommandHandler.execute(newOrderSubmittedCommand);
    }

}
