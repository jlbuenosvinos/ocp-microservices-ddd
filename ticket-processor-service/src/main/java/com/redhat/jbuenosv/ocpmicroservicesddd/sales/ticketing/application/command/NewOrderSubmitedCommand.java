package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class NewOrderSubmitedCommand implements Command {

   private Order order;

    /**
     * Default Constructor
     * @param order New order
     */
   public NewOrderSubmitedCommand(Order order) {
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
