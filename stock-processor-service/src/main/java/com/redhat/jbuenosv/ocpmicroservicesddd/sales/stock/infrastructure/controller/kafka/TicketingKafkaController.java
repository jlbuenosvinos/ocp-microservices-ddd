package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.controller.kafka;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.process.TicketProcessManagerImpl;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service.StockService;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.builder.TicketBuilder;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
class TicketingKafkaController {

    public static final Logger logger = LoggerFactory.getLogger(TicketingKafkaController.class);

    @Autowired
    private TicketBuilder ticketBuilder;

    @Autowired
    private StockService stockService;

    @Autowired
    private TicketProcessManagerImpl ticketProcessManager;

    /**
     * Default constructor
     */
    public TicketingKafkaController() {
    }

    @KafkaListener(topics = "${ticketing.kafka.tickets.topic}", groupId = "1")
    public void receiveTicket(String ticketPayLoad) {
        logger.debug("receiveTicket start.");
        Ticket ticketEvent = null;

        try {
            ticketEvent = ticketBuilder.build(ticketPayLoad);
            logger.debug("receiveTicket ticketEvent object [{}].",ticketEvent.toJson());
            ticketProcessManager.processTicket(ticketEvent);
        }
        catch(Exception e) {
            logger.debug("Error processing the ticket event due to an Exception [{}].",e.getMessage());
            throw new StockApplicationException(e);
        }

    }

}
