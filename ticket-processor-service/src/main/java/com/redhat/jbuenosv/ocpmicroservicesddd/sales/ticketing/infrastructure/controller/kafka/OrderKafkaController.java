package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.controller.kafka;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception.TicketApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.process.OrderTicketsProcessManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by jlbuenosvinos.
 */
@Component
@EnableKafka
@EnableTransactionManagement
public class OrderKafkaController {

    public static final Logger logger = LoggerFactory.getLogger(OrderKafkaController.class);

    @Autowired
    private OrderTicketsProcessManagerImpl orderTicketsProcessManager;

    @KafkaListener(topics = "${ticketing.kafka.orders.commands.topic}", groupId = "1", concurrency = "3")
    public void receiveOrder(String orderPayLoad) {
        logger.debug("receiveOrder start.");
        try {
            logger.debug("Order payload [{}]",orderPayLoad);





        }
        catch(Exception e) {
            logger.debug("Error processing the order event due to an Exception [{}].",e.getMessage());
            throw new TicketApplicationException(e);
        }

    }

}
