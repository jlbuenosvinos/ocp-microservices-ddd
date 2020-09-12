package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockRemoveCommandHandler implements CommandHandler {

    public static final Logger logger = LoggerFactory.getLogger(StockRemoveCommandHandler.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockRemoveCommand stockRemoveCommand;

    /**
     * Executes the command
     * @param command command
     */
    @Override
    public void execute(Command command) {
        this.stockRemoveCommand = (StockRemoveCommand) command;
        stockRepository.removeStock();
    }

}
