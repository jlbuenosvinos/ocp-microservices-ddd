package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.controller;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;

/**
 * Created by jlbuenosvinos.
 */
public class StockValueControllerResponseAdapter {

    private StockValue stockValue;
    private StockValueControllerResponse stockValueControllerResponse;

    public StockValueControllerResponseAdapter() {
        this.stockValueControllerResponse = new StockValueControllerResponse();
    }

    public StockValueControllerResponseAdapter(StockValue stockValue) {
        this.stockValue = stockValue;
        this.stockValueControllerResponse = new StockValueControllerResponse(stockValue.getStoreId(),stockValue.getProductId(),stockValue.getUnits());
    }

    public void setStockValue(StockValue stockValue) {
        this.stockValue = stockValue;
        this.stockValueControllerResponse.setStore_id(this.stockValue.getStoreId());
        this.stockValueControllerResponse.setProduct_id(this.stockValue.getProductId());
        this.stockValueControllerResponse.setUnits(this.stockValue.getUnits());
    }

    public StockValueControllerResponse getStockValueControllerResponse() {
        return this.stockValueControllerResponse;
    }

}
