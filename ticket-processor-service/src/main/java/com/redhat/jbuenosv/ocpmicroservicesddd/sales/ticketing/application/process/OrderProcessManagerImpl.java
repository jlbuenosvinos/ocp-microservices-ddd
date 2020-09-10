package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command.NewOrderSubmittedCommand;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command.NewOrderCommandHandler;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jlbuenosvinos.
 */
@Service
public class OrderProcessManagerImpl {

    public static final Logger logger = LoggerFactory.getLogger(OrderProcessManagerImpl.class);

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
