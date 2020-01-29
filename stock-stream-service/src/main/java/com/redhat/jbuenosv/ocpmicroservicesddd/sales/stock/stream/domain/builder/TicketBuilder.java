package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.builder;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Order builder
 * Created by jlbuenosvinos.
 */
@Component
public class TicketBuilder {

    public static final Logger logger = LoggerFactory.getLogger(TicketBuilder.class);

    public TicketKey buildKey(String ticket) {
        JsonNode nameNode;
        JsonNode jsonItem;
        TicketKey newTicketKey = new TicketKey();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(ticket);
            nameNode = rootNode.path("store_id");
            newTicketKey.setStoreId(nameNode.intValue());
            nameNode = rootNode.path("ticket_id");
            newTicketKey.setTicketId(nameNode.textValue());
            logger.debug("newTicketKey ready.",newTicketKey);
        }
        catch(Exception e) {
            logger.error("Unable to build the ticket key. [{}]",e.getMessage());
            throw new StockApplicationException(e) ;
        }

        return newTicketKey;
    }  // end buildKey

    public TicketValue buildValue(String ticket) {
        JsonNode nameNode;
        JsonNode jsonItem;
        TicketValue newTicketValue = new TicketValue();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(ticket);
            nameNode = rootNode.path("store_id");
            newTicketValue.setStoreId(nameNode.intValue());
            nameNode = rootNode.path("ticket_id");
            newTicketValue.setTicketId(nameNode.textValue());
            nameNode = rootNode.path("item").path("id");
            newTicketValue.setId(nameNode.textValue());
            nameNode = rootNode.path("item").path("units");
            newTicketValue.setUnits(nameNode.intValue());
        }
        catch(Exception e) {
            logger.error("Unable to build the ticket. [{}]",e.getMessage());
            throw new StockApplicationException(e) ;
        }

        return newTicketValue;
    } // end buildValue

}
