package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command.*;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StoreValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
@Service
public class StockQueryProcessManagerImpl implements  StockQueryProcessManager {

    @Autowired
    StockByStoreQueryCommand stockByStoreQueryCommand;

    @Autowired
    StockByStoreProductQueryCommand stockByStoreProductQueryCommand;

    @Autowired
    StoreQueryCommand storeQueryCommand;

    @Autowired
    StockRemoveCommand stockRemoveCommand;

    @Autowired
    StockByStoreQueryCommandCommandHandler stockByStoreQueryCommandCommandHandler;

    @Autowired
    StockByStoreProductQueryCommandCommandHandler stockByStoreProductQueryCommandCommandHandler;

    @Autowired
    StoreQueryCommandHandler storeQueryCommandHandler;

    @Autowired
    StockRemoveCommandHandler stockRemoveCommandHandler;

    /**
     * Get the current stock for an specific  store
     * @param storeId store id
     * @return stock value
     */
    @Override
    public List<StockValue> processQuery(Integer storeId) {
        stockByStoreQueryCommand.setStoreId(storeId);
        stockByStoreQueryCommandCommandHandler.execute(stockByStoreQueryCommand);
        return stockByStoreQueryCommandCommandHandler.getStockValueList();
    }

    /**
     * Get the current stock for an specific  store
     * @param storeId store id
     * @param productId product id
     * @return stock value
     */
    @Override
    public StockValue processQuery(Integer storeId, String productId) {
        stockByStoreProductQueryCommand.setStoreId(storeId);
        stockByStoreProductQueryCommand.setProductId(productId);
        stockByStoreProductQueryCommandCommandHandler.execute(stockByStoreProductQueryCommand);
        return stockByStoreProductQueryCommandCommandHandler.getStockValue();
    }

    /**
     * Get the store list
     * @return stores list
     */
    @Override
    public List<StoreValue> processQuery() {
        storeQueryCommandHandler.execute(storeQueryCommand);
        return storeQueryCommandHandler.getStoreValueList();
    }

    /**
     * Removes the stock
     */
    @Override
    public void processDelete() {
        stockRemoveCommandHandler.execute(stockRemoveCommand);
    }

}
