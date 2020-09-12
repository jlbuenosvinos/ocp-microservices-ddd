package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockByStoreProductQueryCommandCommandHandler implements CommandHandler {

    public static final Logger logger = LoggerFactory.getLogger(StockByStoreProductQueryCommandCommandHandler.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockByStoreProductQueryCommand stockByStoreProductQueryCommand;

    /**
     * Executes the command
     * @param command command
     */
    @Override
    public void execute(Command command) {
        this.stockByStoreProductQueryCommand = (StockByStoreProductQueryCommand)command;
        String productId = this.stockByStoreProductQueryCommand.getProductId();
        Integer storeId = this.stockByStoreProductQueryCommand.getStoreId();
        this.stockByStoreProductQueryCommand.setStockValue(stockRepository.findByStoreIdProductId(storeId,productId));
    }

    /**
     * Gets the stock query result by store id
     * @return stock query result by store id
     */
    public StockValue getStockValue() {
        return this.stockByStoreProductQueryCommand.getStockValue();
    }

}
