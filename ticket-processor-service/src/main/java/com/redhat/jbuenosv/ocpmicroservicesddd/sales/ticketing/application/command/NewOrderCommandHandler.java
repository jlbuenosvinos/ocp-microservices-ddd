package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka.order.OrderKafkaPublisherConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class NewOrderCommandHandler implements  CommandHandler {

    @Autowired
    OrderKafkaPublisherConfig orderKafkaPublisherConfig;

    /**
     * Executes the command
     *
     * @param command
     */
    @Override
    public void execute(Command command) {
        NewOrderSubmitedCommand newOrderSubmitedCommand = (NewOrderSubmitedCommand)command;
        Order newOrder = newOrderSubmitedCommand.getOrder();
        orderKafkaPublisherConfig.publisher().publish(orderKafkaPublisherConfig.getKafkaOrdersTopicName(),newOrder.getOrderId(),newOrder.toJson());
    }

}
