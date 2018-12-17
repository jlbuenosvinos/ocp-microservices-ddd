package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StoreValue;

import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
public interface StoreRepository {

    /**
     * Gets the stores list
     * @return stores list
     */
    List<StoreValue> findAll();

}
