package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.CommonConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception.TicketApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEventKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.OrderLineType;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Ticket;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.TicketItem;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka.ticket.TicketKafkaPublisherConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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

    @Autowired
    TicketKafkaPublisherConfig ticketKafkaPublisherConfig;

    /**
     * Executes the command
     * @param command command to be executed
     */
    @Override
    public void execute(Command command) {
        NewOrderSubmittedCommand newOrderSubmittedCommand = (NewOrderSubmittedCommand)command;
        Order order = newOrderSubmittedCommand.getOrder();
        Ticket ticket = null;
        TicketItem ticketItem = null;
        int numTickets = order.getItems().size();
        TicketGeneratedEventKey ticketGeneratedEventKey = null;
        ArrayList<TicketGeneratedEventKey> ticketGeneratedEventKeyList = new ArrayList<TicketGeneratedEventKey>(numTickets);
        ArrayList<String> ticketGeneratedEventValueList = new ArrayList<String>(numTickets);
        Integer newUnits = 0;

        try {

            for(int i = 0 ; i < numTickets ; i++) {

                ticketGeneratedEventKey = new TicketGeneratedEventKey();
                ticketGeneratedEventKey.setTicketId(uuidGenerator.getUuid());
                ticketGeneratedEventKey.setStoreId(order.getStoreId());

                ticketGeneratedEventKeyList.add(ticketGeneratedEventKey);

                ticket = new Ticket();
                ticket.setTicketId(ticketGeneratedEventKey.getTicketId());
                ticket.setStoreId(order.getStoreId());
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

                ticketGeneratedEventValueList.add(ticket.toJson());

            }
            // time to send the tickets events
            ticketKafkaPublisherConfig.publishAll(ticketKafkaPublisherConfig.getKafkaTicketsTopicName(),ticketGeneratedEventKeyList,ticketGeneratedEventValueList);

        }
        catch(Exception e) {
            throw new TicketApplicationException(e);
        }

    }

}
