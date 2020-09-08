package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
public class NewOrderSubmittedCommand implements Command {

    private final Order order;

    /**
     * Default Constructor
     * @param order New order
     */
   public NewOrderSubmittedCommand(Order order) {
       this.order = order;
   }

    /**
     *  Gets the order
      * @return the command order
     */
   public Order getOrder() {
        return order;
    }

}
