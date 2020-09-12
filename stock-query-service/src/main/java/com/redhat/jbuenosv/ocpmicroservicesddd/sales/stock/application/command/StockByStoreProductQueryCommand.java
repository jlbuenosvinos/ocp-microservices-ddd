package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockByStoreProductQueryCommand implements Command {

    public static final Logger logger = LoggerFactory.getLogger(StockByStoreProductQueryCommand.class);

    Integer storeId;
    String productId;
    private StockValue stockValue;

    public StockValue getStockValue() {
        return stockValue;
    }

    public void setStockValue(StockValue stockValue) {
        this.stockValue = stockValue;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
