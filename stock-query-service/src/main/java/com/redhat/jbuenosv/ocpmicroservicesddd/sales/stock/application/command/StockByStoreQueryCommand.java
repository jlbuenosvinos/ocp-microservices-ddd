package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockByStoreQueryCommand implements Command {

    public static final Logger logger = LoggerFactory.getLogger(StockByStoreQueryCommand.class);

    Integer storeId;
    private List<StockValue> stockValueList;

    public List<StockValue> getStockValueList() {
        return stockValueList;
    }

    public void setStockValueList(List<StockValue> stockValueList) {
        this.stockValueList = stockValueList;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

}
