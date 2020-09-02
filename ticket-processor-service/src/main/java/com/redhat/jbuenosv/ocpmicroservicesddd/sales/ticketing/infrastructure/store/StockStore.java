package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store;

import com.google.common.eventbus.Subscribe;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.ActiveMQConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.CommonConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception.TicketApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;

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
@Component
public class StockStore implements EventStore {

    public static final Logger logger = LoggerFactory.getLogger(StockStore.class);

    @Autowired
    ActiveMQConfig mqConfig;

    @Autowired
    CommonConfig commonConfig;

    /**
     * Saves a ticket event
     * @param event ticket event
     */
    @Subscribe
    public void store(TicketGeneratedEvent event) {
        String stockProcessorUri = commonConfig.getTicketingStockProcessorUri();
        RestTemplate restTemplate = new RestTemplate();
        String stockProcessorPayLoad = event.getTicket().toJson();

        ResponseEntity<String> response = restTemplate.postForEntity(stockProcessorUri,stockProcessorPayLoad,String.class);
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            logger.error("TicketEvent has NOT been processed.");
            throw new TicketApplicationException("Unable to send the TicketEvent to the StockProcessor.");
        }
        else {
            logger.debug("TicketEvent has been processed.");
        }
    }

}
