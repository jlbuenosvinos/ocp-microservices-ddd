package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.controller;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StoreValue;

/**
 * Created by jlbuenosvinos.
 */
public class StoreValueControllerResponseAdapter {

    private StoreValue storeValue;
    private StoreValueControllerResponse storeValueControllerResponse;

    public StoreValueControllerResponseAdapter() {
        this.storeValueControllerResponse = new StoreValueControllerResponse();
    }

    public StoreValueControllerResponseAdapter(StoreValue storeValue) {
        this.storeValue = storeValue;
        this.storeValueControllerResponse = new StoreValueControllerResponse(storeValue.getStoreId(),storeValue.getStoreName());
    }

    public void setStoreValue(StoreValue storeValue) {
        this.storeValue = storeValue;
        this.storeValueControllerResponse.setStore_id(this.storeValue.getStoreId());
        this.storeValueControllerResponse.setStore_name(this.storeValue.getStoreName());
    }

    public StoreValueControllerResponse getStoreValueControllerResponse() {
        return this.storeValueControllerResponse;
    }

}
