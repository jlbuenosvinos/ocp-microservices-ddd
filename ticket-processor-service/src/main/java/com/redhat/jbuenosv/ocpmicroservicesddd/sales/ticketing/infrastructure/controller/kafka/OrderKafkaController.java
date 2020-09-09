package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.controller.kafka;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception.TicketApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Created by jlbuenosvinos.
 */
@Component
@EnableKafka
@EnableTransactionManagement
public class OrderKafkaController {

    public static final Logger logger = LoggerFactory.getLogger(OrderKafkaController.class);

    @KafkaListener(topics = "${ticketing.kafka.orders.commands.topic}", containerFactory = "order-consumer-factory", groupId = "1")
    //@Transactional("order-consumer-transaction-manager")
    @TransactionalEventListener
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
