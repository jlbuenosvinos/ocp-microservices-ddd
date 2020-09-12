package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StoreValue;

import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
public interface StockQueryProcessManager {

    /**
     * Get the current stock for an specific  store
     * @param storeId store id
     * @return stock value
     */
    List<StockValue> processQuery(Integer storeId);

    /**
     * Get the current stock for an specific  store
     * @param storeId store id
     * @param productId product id
     * @return stock value
     */
    StockValue processQuery(Integer storeId, String productId);

    /**
     * Get the store list
     * @return stores list
     */
    List<StoreValue> processQuery();

    /**
     * Removes the stock
     */
    void processDelete();

}
