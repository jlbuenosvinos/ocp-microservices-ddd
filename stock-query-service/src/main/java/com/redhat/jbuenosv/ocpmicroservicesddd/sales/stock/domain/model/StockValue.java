package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a stock value element for the stock data grid cache
 * Created by jlbuenosvinos.
 */
public class StockValue implements Serializable {

    private Integer storeId;
    private String productId;
    private Integer units;

    /**
     * Default constructor
     */
    public StockValue() {
        this.storeId = 1;
        this.productId = "undefined";
        this.units = 0;
    }

    /**
     * StockValue Constructor
     * @param storeId store id
     * @param productId product id
     * @param units  number of available products
     */
    public StockValue(Integer storeId, String productId, Integer units) {
        this.storeId = storeId;
        this.productId = productId;
        this.units = units;
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

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockValue)) return false;
        StockValue that = (StockValue) o;
        return Objects.equals(getStoreId(), that.getStoreId()) &&
                Objects.equals(getProductId(), that.getProductId()) &&
                Objects.equals(getUnits(), that.getUnits());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStoreId(), getProductId(), getUnits());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" storeId: " + getStoreId() + NEW_LINE);
        result.append(" productId: " + getProductId() + NEW_LINE);
        result.append(" units: " + getUnits() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

    /**
     * Gets the JSON representation
     * @return ticket json representation
     */
    public String toJson() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append("{" );
        result.append("    \"store_id\": " + getStoreId() + ",");
        result.append("    \"product_id\": " + getProductId() + ",");
        result.append("    \"units\": " + getUnits());
        result.append("}");
        return result.toString();
    }

}
