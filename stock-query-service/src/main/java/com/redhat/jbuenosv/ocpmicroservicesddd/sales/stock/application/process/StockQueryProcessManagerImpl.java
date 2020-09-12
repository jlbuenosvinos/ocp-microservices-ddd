package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
@Service
public class StockQueryProcessManagerImpl implements  StockQueryProcessManager {

    @Override
    public List<StockValue> processQuery(Integer storeId) {


        return null;

    }

}
