package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.builder;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.TicketItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Order builder
 * Created by jlbuenosvinos.
 */
@Component
public class TicketBuilder {

    public static final Logger logger = LoggerFactory.getLogger(TicketBuilder.class);

    /**
     * Builds a Ticket type reading its JSON representation
     * @param ticket ticket item
     * @return ticket object based on the event representation
     */
    public Ticket build(Message ticket) {
        Ticket newTicket;
        TextMessage ticketMessage = (TextMessage)ticket;
        try {
            newTicket = build(ticketMessage.getText());
        }
        catch(Exception e) {
            logger.error("Unable to build the ticket. [{}]",e.getMessage());
            throw new StockApplicationException(e) ;
        }
        return newTicket;
    }

    /**
     * Builds a Ticket type reading its JSON representation
     * @param ticket ticket item
     * @return ticket object based on the event representation
     */
    public Ticket build(String ticket) {
        JsonNode nameNode;
        JsonNode jsonItem;
        Ticket newTicket = new Ticket();
        TicketItem ticketItem = new TicketItem();

        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(ticket);

            nameNode = rootNode.path("store_id");
            Integer nodeStoreId = nameNode.intValue();
            newTicket.setStoreId(nodeStoreId);

            nameNode = rootNode.path("ticket_id");
            String nodeTicketId = nameNode.textValue();
            newTicket.setTicketId(nodeTicketId);

            jsonItem = rootNode.path("item").path("id");
            String nodeId = jsonItem.textValue();

            jsonItem = rootNode.path("item").path("units");
            Integer units = jsonItem.asInt();

            ticketItem.setId(nodeId);
            ticketItem.setUnits(units);
        }
        catch(Exception e) {
            logger.error("Unable to build the ticket. [{}]",e.getMessage());
            throw new StockApplicationException(e) ;
        }

        newTicket.setItem(ticketItem);
        return newTicket;
    }

}
