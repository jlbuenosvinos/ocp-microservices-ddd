package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a stock key element for the stock data grid cache
 * Created by jlbuenosvinos.
 */
public class StockKey implements Serializable {

    private Integer storeId;
    private String productId;

    /**
     * Default constructor
     */
    public StockKey() {
        this.storeId = 1;
        this.productId = "undefined";
    }

    /**
     * Constructor
     * @param storeId store id
     * @param productId product id
     */
    public StockKey(Integer storeId, String productId) {
        this.storeId = storeId;
        this.productId = productId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockKey)) return false;
        StockKey stockKey = (StockKey) o;
        return Objects.equals(getStoreId(), stockKey.getStoreId()) &&
                Objects.equals(getProductId(), stockKey.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStoreId(), getProductId());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" storeId: " + getStoreId() + NEW_LINE);
        result.append(" productId: " + getProductId() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

}
