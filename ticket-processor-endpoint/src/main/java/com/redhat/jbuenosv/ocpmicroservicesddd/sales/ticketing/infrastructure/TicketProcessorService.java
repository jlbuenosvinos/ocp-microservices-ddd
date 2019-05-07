package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.CommonConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception.TicketApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jlbuenosvinos.
 */
@Component("ticketProcessorService")
public class TicketProcessorService {

    public static final Logger logger = LoggerFactory.getLogger(TicketProcessorService.class);

    @Autowired
    CommonConfig commonConfig;

    /**
     * Default constructor
     */
    public TicketProcessorService() {
    }

    /**
     * processOrder
     * @param order
     * @return
     */
    public ResponseEntity<?> processOrder(String order) {
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
