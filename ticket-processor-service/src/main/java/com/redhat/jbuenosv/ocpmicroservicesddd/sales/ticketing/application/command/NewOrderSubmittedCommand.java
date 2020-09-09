package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class NewOrderSubmittedCommand implements Command {

    private Order order;

    /**
     * Default constructor
     */
    public NewOrderSubmittedCommand() {
    }

    /**
     *  Gets the order
      * @return the command order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the orden
     * @param order order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

}
