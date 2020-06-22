package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.builder;

import java.util.Iterator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception.TicketApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.OrderLine;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.OrderLineSize;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.OrderLineType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Order builder
 * Created by jlbuenosvinos.
 */
@Component
public class OrderBuilder {

    public static final Logger logger = LoggerFactory.getLogger(OrderBuilder.class);

    /**
     * Builds an Order type reading its JSON representation
     * @param order Order JSON representation
     * @return
     */
    public Order build(String order) {
        Order newOrder = new Order();
        JsonNode nameNode;
        JsonNode jsonItem;
        OrderLine newOrderLine;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(order);

            nameNode = rootNode.path("store_id");
            newOrder.setStoreId(nameNode.intValue());

            logger.debug("stored_id populated.");

            nameNode = rootNode.path("order_id");
            newOrder.setOrderId(nameNode.textValue());

            logger.debug("order_id populated.");

            JsonNode marksNode = rootNode.path("items");
            Iterator<JsonNode> iterator = marksNode.elements();

            while (iterator.hasNext()) {
                jsonItem = iterator.next();
                newOrderLine = new OrderLine();
                nameNode = jsonItem.path("id");
                newOrderLine.setId(nameNode.textValue());
                nameNode = jsonItem.path("name");
                newOrderLine.setName(nameNode.textValue());
                nameNode = jsonItem.path("size");
                newOrderLine.setSize(OrderLineSize.valueOf(nameNode.textValue().toUpperCase()));
                nameNode = jsonItem.path("units");
                newOrderLine.setUnits(nameNode.intValue());
                nameNode = jsonItem.path("operation_type");
                newOrderLine.setType(OrderLineType.valueOf(nameNode.textValue().toUpperCase()));
                newOrder.addItem(newOrderLine);
            }

            logger.debug("items populated.");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw new TicketApplicationException(e) ;
        }
        return newOrder;
    }

}
