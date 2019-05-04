package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.controller;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.CommonConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception.TicketApplicationException;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by jlbuenosvinos.
 */
@RestController
@Api(value="/api",produces ="application/json")
@RequestMapping("/api")
public class TicketProcessorServiceController {

    public static final Logger logger = LoggerFactory.getLogger(TicketProcessorServiceController.class);

    @Autowired
    CommonConfig commonConfig;

    /**
     * Default constructor
     */
    public TicketProcessorServiceController() {
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseEntity<?> processOrder(@RequestBody String order, UriComponentsBuilder ucBuilder) {
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();

        try {
            response = restTemplate.postForEntity(commonConfig.getTicketingTicketProcessorUri(),order,String.class);
            if (!response.getStatusCode().equals(HttpStatus.OK)) {
                logger.error("TicketEvent has NOT been processed.");
                throw new TicketApplicationException("Unable to send the TicketEvent to the StockProcessor.");
            }
            else {
                logger.debug("TicketEvent has been processed.");
            }
        }
        catch(TicketApplicationException e) {
            response = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.debug("Error processing the order due to a TicketApplicationException [{}]",e.getMessage());
        }

        return response;
    }

}
