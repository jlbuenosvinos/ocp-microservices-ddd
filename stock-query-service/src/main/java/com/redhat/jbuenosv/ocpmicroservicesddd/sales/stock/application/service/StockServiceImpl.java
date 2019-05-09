package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StoreValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository.StockRepository;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service includes the main logic for the ticketing projection service
 * Created by jlbuenosvinos.
 */
@Service
public class StockServiceImpl implements StockService {

    public static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StoreRepository storeRepository;

    /**
     * Get the current stock for an specific product and store
     * @param storeId store id
     * @return stock value
     */
    public List<StockValue> getStock(Integer storeId) {
        return stockRepository.findByByStoreId(storeId);
    }

    /**
     * Get the current stock for an specific product and store
     * @param storeId store id
     * @param productId product id
     * @return stock value
     */
    public StockValue getStock(Integer storeId, String productId) {
        return stockRepository.findByStoreIdProductId(storeId,productId);
    }

    /**
     * Removes the stock
     * @return void
     */
    public void removeStock() {
        stockRepository.removeStock();
    }

    /**
     * Get the stores list
     * @return stores list
     */
    public List<StoreValue> getStores() {
        return storeRepository.findAll();
    }

}
