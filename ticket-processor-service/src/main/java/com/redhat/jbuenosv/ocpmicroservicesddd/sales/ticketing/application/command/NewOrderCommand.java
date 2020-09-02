package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class NewOrderCommand implements Command {

   private Order order;

    /**
     * Default Constructor
     * @param order New order
     */
   public NewOrderCommand(Order order) {
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
