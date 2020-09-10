package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.CommonConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception.TicketApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.OrderLineType;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Ticket;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.TicketItem;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class NewOrderTicketsCommandHandler implements  CommandHandler {

    public static final Logger logger = LoggerFactory.getLogger(NewOrderTicketsCommandHandler.class);

    @Autowired
    UUIDGenerator uuidGenerator;

    @Autowired
    CommonConfig config;

    /**
     * Executes the command
     * @param command command to be executed
     */
    @Override
    public void execute(Command command) {
        NewOrderSubmittedCommand newOrderSubmittedCommand = (NewOrderSubmittedCommand)command;
        Order order = newOrderSubmittedCommand.getOrder();
        String orderId = order.getOrderId();
        String orderJson = order.toJson();
        Ticket ticket = null;
        TicketItem ticketItem = null;
        int numTickets = order.getItems().size();
        TicketGeneratedEvent ticketEvent = null;
        List<TicketGeneratedEvent> ticketEvents = new ArrayList<TicketGeneratedEvent>();
        Integer newUnits = 0;

        try {

            for(int i = 0 ; i < numTickets ; i++) {
                ticketEvent = new TicketGeneratedEvent();
                ticketEvent.setEventId(uuidGenerator.getUuid());
                ticketEvent.setEventType(TicketGeneratedEvent.class.getName());
                ticket = new Ticket();
                ticket.setStoreId(order.getStoreId());
                ticket.setTicketId(uuidGenerator.getUuid());
                ticketItem = new TicketItem();
                ticketItem.setId(order.getItems().get(i).getId());
                if (order.getItems().get(i).getType() == OrderLineType.RETURN) {
                    newUnits = order.getItems().get(i).getUnits();
                }
                else {
                    newUnits = order.getItems().get(i).getUnits() * -1;
                }
                ticketItem.setUnits(newUnits);
                ticket.setItem(ticketItem);
                ticketEvent.setTicket(ticket);
                logger.debug("Added new Ticket: {}",ticket.toString());
                ticketEvent.setEventVersion(config.getTicketingEventVersion());
                ticketEvents.add(ticketEvent);
            }
            // time to send the tickets events




        }
        catch(Exception e) {
            throw new TicketApplicationException(e);
        }

    }

}
