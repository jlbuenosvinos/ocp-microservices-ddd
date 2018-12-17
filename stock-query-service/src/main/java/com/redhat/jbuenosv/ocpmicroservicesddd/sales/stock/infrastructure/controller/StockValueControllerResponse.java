package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.controller;

import java.io.Serializable;

/**
 * Created by jlbuenosvinos.
 */
public class StockValueControllerResponse implements Serializable {

    private Integer store_id;
    private String product_id;
    private Integer units;

    public StockValueControllerResponse() {
        this.store_id = -1;
        this.product_id = "undefined";
        this.units = 0;
    }

    public StockValueControllerResponse(Integer store_id, String product_id, Integer units) {
        this.store_id = store_id;
        this.product_id = product_id;
        this.units = units;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

}
