package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockRemoveCommand implements Command {

    public static final Logger logger = LoggerFactory.getLogger(StockRemoveCommand.class);

}
