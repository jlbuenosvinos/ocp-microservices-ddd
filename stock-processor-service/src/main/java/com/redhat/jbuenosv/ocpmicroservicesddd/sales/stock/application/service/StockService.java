package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;

import java.util.List;

/**
 * This service includes the main logic for the ticketing projection service
 * Created by jlbuenosvinos.
 */
public interface StockService {

    /**
     * Projects a ticket
     * @param ticket ticket to be processed
     */
    void processTicket(Ticket ticket);

    /**
     * Get the current stock for an specific product and store
     * @param storeId store id
     * @return stock value
     */
    List<StockValue> getStock(Integer storeId);

    /**
     * Get the current stock for an specific product and store
     * @param storeId store id
     * @param productId product id
     * @return stock value
     */
    StockValue getStock(Integer storeId, String productId);

}
