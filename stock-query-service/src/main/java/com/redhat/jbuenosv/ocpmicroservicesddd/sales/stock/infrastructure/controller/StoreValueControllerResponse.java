package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.controller;

import java.util.Objects;

/**
 * Created by jlbuenosvinos.
 */
public class StoreValueControllerResponse {

    private Integer store_id;
    private String store_name;


    public StoreValueControllerResponse() {
        this.store_id = 0;
        this.store_name = "undefined";
    }

    public StoreValueControllerResponse(Integer store_id, String store_name) {
        this.store_id = store_id;
        this.store_name = store_name;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreValueControllerResponse)) return false;
        StoreValueControllerResponse that = (StoreValueControllerResponse) o;
        return Objects.equals(getStore_id(), that.getStore_id()) &&
                Objects.equals(getStore_name(), that.getStore_name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStore_id(), getStore_name());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" store_id: " + getStore_id() + NEW_LINE);
        result.append(" store_name: " + getStore_name() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

}
