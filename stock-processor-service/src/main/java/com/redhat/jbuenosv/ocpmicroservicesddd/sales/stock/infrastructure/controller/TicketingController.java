package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.controller;


import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration.ActiveMQConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service.StockService;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.builder.TicketBuilder;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by jlbuenosvinos.
 */
@Component
@EnableJms
class TicketingController {

    public static final Logger logger = LoggerFactory.getLogger(TicketingController.class);

    @Autowired
    private ActiveMQConfig activeMQConfig;

    @Autowired
    private TicketBuilder ticketBuilder;

    @Autowired
    private StockService stockService;

    /**
     * Default constructor
     */
    public TicketingController() {
    }

    /**
     * Receives a ticket event
     * @param ticket
     * @param headers
     * @param message
     * @param session
     */
    @Timed
    @JmsListener(destination = "${ticketing.activemq.tickets.topic}", containerFactory = "jmsListenerContainerFactory")
    public void receiveTicket(@Payload String ticket, @Headers MessageHeaders headers, Message message, Session session) {
        Ticket ticketEvent = null;
        String ticketEventId = null;

        try {
            ticketEvent = ticketBuilder.build(message);
            ticketEventId = message.getStringProperty("sales-event-id");
            logger.debug("Message received [{},{}]",message.getStringProperty("sales-event-type"),ticket);
            stockService.processTicket(ticketEvent);
            logger.debug("The ticket [{}] event has been processed ok.",ticketEventId);
        }
        catch(Exception e) {
            logger.debug("Error processing the ticket event due to an Exception [{}].",e.getMessage());
            throw new StockApplicationException(e);
        }

    }

}
