package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StoreValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StoreQueryCommand implements Command {

    public static final Logger logger = LoggerFactory.getLogger(StoreQueryCommand.class);

    private List<StoreValue> storeValueList;

    public List<StoreValue> getStoreValueList() {
        return storeValueList;
    }

    public void setStoreValueList(List<StoreValue> storeValueList) {
        this.storeValueList = storeValueList;
    }
}
