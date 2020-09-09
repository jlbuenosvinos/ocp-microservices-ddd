package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka.order.OrderKafkaPublisherConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class NewOrderCommandHandler implements  CommandHandler {

    public static final Logger logger = LoggerFactory.getLogger(NewOrderCommandHandler.class);

    @Autowired
    OrderKafkaPublisherConfig config;

    //@Autowired
    //@Qualifier("order-control-template")
    //KafkaTemplate<String,String> kafkaTemplate;

    /**
     * Executes the command
     * @param command command to be executed
     */
    @Override
    public void execute(Command command) {
        NewOrderSubmittedCommand newOrderSubmittedCommand = (NewOrderSubmittedCommand)command;
        Order newOrder = newOrderSubmittedCommand.getOrder();
        String orderId = newOrder.getOrderId();
        String orderJson = newOrder.toJson();
        String ordersTopicName = config.getKafkaOrdersTopicName() ;

        config.publish(ordersTopicName,orderId,orderJson);

        /*
        if (kafkaTemplate != null) {
            kafkaTemplate.executeInTransaction(t -> t.send(ordersTopicName,orderId,orderJson));
            // kafkaTemplate.send(ordersTopicName,orderId,orderJson);
        }
        else {
            logger.error("Kafka template is null.");
        }
        */


    }

}
