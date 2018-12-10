package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by jlbuenosvinos.
 */
public class StoreValue implements Serializable {

    private Integer storeId;
    private String storeName;

    public StoreValue() {
        this.storeId = 0;
        this.storeName = "undefined";
    }

    public StoreValue(Integer storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreValue)) return false;
        StoreValue that = (StoreValue) o;
        return Objects.equals(getStoreId(), that.getStoreId()) &&
                Objects.equals(getStoreName(), that.getStoreName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStoreId(), getStoreName());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" storeId: " + getStoreId() + NEW_LINE);
        result.append(" storeName: " + getStoreName() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

}
