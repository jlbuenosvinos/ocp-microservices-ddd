package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository.StockRepository;
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
public class StockByStoreQueryCommandCommandHandler implements CommandHandler {

    public static final Logger logger = LoggerFactory.getLogger(StockByStoreQueryCommandCommandHandler.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockByStoreQueryCommand stockByStoreQueryCommand;

    /**
     * Executes the command
     * @param command command
     */
    @Override
    public void execute(Command command) {
        this.stockByStoreQueryCommand = (StockByStoreQueryCommand)command;
        this.stockByStoreQueryCommand.setStockValueList(stockRepository.findByByStoreId(this.stockByStoreQueryCommand.getStoreId()));
    }

    /**
     * Gets the stock query result by store id
     * @return stock query result by store id
     */
    public List<StockValue> getStockValueList() {
        return this.stockByStoreQueryCommand.getStockValueList();
    }

}
