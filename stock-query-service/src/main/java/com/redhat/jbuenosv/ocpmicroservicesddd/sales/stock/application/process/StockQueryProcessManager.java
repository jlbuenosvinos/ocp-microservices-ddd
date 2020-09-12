package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;

import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
public interface StockQueryProcessManager {

    List<StockValue> processQuery(Integer storeId);

}
