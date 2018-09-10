package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.controller;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception.TicketApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.builder.OrderBuilder;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.service.TicketService;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by jlbuenosvinos.
 */
@RestController
@RequestMapping("/api")
class OrderController {

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderBuilder orderBuilder;

    @Autowired
    private TicketService ticketService;

    /**
     * Default constructor
     */
    public OrderController() {
    }

    /**
     * Physical store order process
     * @param order physical store order
     * @param ucBuilder uri builder
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity<?> processOrder(@RequestBody String order, UriComponentsBuilder ucBuilder) {
        ResponseEntity orderResponse;

        try {
            Order newOrder = orderBuilder.build(order.toString());
            ticketService.processOrder(newOrder);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/order/{" + newOrder.getOrderId() + "}").buildAndExpand(newOrder.getOrderId()).toUri());
            logger.debug("Order [{}] has been created.", newOrder.getOrderId());
            orderResponse = new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }
        catch(TicketApplicationException e) {
            orderResponse = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.debug("Error processing the order due to a TicketApplicationException [{}]",e.getMessage());
        }

        return orderResponse;
    }
}
