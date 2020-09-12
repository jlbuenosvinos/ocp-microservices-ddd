package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command.StockByStoreProductQueryCommand;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command.StockByStoreProductQueryCommandCommandHandler;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command.StockByStoreQueryCommand;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command.StockByStoreQueryCommandCommandHandler;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
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
    StockByStoreQueryCommandCommandHandler stockByStoreQueryCommandCommandHandler;

    @Autowired
    StockByStoreProductQueryCommandCommandHandler stockByStoreProductQueryCommandCommandHandler;

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
     *
     * @param storeId   store id
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

}
