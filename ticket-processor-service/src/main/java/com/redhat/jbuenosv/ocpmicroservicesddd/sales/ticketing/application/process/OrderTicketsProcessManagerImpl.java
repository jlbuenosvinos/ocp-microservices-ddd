package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command.NewOrderSubmittedCommand;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command.NewOrderTicketsCommandHandler;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jlbuenosvinos.
 */
@Service
public class OrderTicketsProcessManagerImpl implements OrderTicketsProcessManager {

    public static final Logger logger = LoggerFactory.getLogger(OrderTicketsProcessManagerImpl.class);

    @Autowired
    NewOrderTicketsCommandHandler newOrderTicketsCommandHandler;

    @Autowired
    NewOrderSubmittedCommand newOrderSubmittedCommand;

    /**
     * Process the tickets of an Order
     * @param order Physical order
     */
    @Override
    public void processOrderTickets(Order order) {
        newOrderSubmittedCommand.setOrder(order);
        newOrderTicketsCommandHandler.execute(newOrderSubmittedCommand);
    }

}
