package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.controller;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service.StockService;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.builder.TicketBuilder;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by jlbuenosvinos.
 */
@RestController
@Api(value="/api",produces ="application/json")
@RequestMapping("/api")
public class TicketingRestController {

    public static final Logger logger = LoggerFactory.getLogger(TicketingRestController.class);

    @Autowired
    private TicketBuilder ticketBuilder;

    @Autowired
    private StockService stockService;

    /**
     * Physical store order process
     * @param ticket functional ticket
     * @param ucBuilder uri builder
     * @return
     */
    @Timed
    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    public ResponseEntity<?> processOrder(@RequestBody String ticket, UriComponentsBuilder ucBuilder) {
        ResponseEntity ticketResponse = null;
        Ticket ticketEvent = null;

        try {
            ticketEvent = ticketBuilder.build(ticket);

            logger.debug("TicketEvent received [{}]",ticket.toString());
            stockService.processTicket(ticketEvent);
            logger.debug("TicketEvent has been processed ok.");

            ticketResponse = new ResponseEntity<String>(HttpStatus.CREATED);
        }
        catch(StockApplicationException e) {
            ticketResponse = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.debug("Error processing the Ticket due to a StockApplicationException [{}]",e.getMessage());
        }

        return ticketResponse;
    }

}
