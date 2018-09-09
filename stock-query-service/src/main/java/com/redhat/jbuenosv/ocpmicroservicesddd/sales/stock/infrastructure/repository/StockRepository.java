package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;

import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
public interface StockRepository {

    /**
     * Get the current stock for an specific product and store
     * @param storeId store id
     * @return stock value
     */
    List<StockValue> findByByStoreId(Integer storeId);

    /**
     * Get the current stock for an specific product and store
     * @param storeId store id
     * @param productId product id
     * @return stock value
     */
    StockValue findByStoreIdProductId(Integer storeId, String productId);

}
