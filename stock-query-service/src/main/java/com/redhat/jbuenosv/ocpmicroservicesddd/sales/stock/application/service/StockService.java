package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StoreValue;

import java.util.List;

/**
 * This service includes the main logic for the ticketing projection service
 * Created by jlbuenosvinos.
 */
public interface StockService {

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

    /**
     * Get the stores list
     * @return stores list
     */
    List<StoreValue> getStores();

    /**
     * Removes the stock
     * @return void
     */
    void removeStock();

}
