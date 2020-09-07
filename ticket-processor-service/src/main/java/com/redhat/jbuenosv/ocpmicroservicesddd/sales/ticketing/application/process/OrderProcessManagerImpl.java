package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command.NewOrderSubmitedCommand;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command.NewOrderCommandHandler;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessManagerImpl {

    @Autowired
    NewOrderCommandHandler orderCommandHandler;

    /**
     * Process an order
     * @param order Physical order
     */
    public void processOrder(Order order) {
        orderCommandHandler.execute(new NewOrderSubmitedCommand(order));
    }

}
