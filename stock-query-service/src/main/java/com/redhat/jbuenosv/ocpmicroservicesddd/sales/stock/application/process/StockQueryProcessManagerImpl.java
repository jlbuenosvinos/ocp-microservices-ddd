package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.process;

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
    StockByStoreQueryCommandCommandHandler stockByStoreQueryCommandCommandHandler;

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

}
