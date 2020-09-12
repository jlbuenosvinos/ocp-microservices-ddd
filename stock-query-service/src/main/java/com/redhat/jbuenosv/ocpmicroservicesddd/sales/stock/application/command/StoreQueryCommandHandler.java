package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StoreValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StoreQueryCommandHandler implements CommandHandler {

    public static final Logger logger = LoggerFactory.getLogger(StoreQueryCommandHandler.class);

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreQueryCommand storeQueryCommand;

    /**
     * Executes the command
     * @param command command
     */
    @Override
    public void execute(Command command) {
        this.storeQueryCommand = (StoreQueryCommand)command;
        this.storeQueryCommand.setStoreValueList(storeRepository.findAll());
    }

    /**
     * Get the store list
     * @return store list
     */
    public List<StoreValue> getStoreValueList() {
        return this.storeQueryCommand.getStoreValueList();
    }

}
