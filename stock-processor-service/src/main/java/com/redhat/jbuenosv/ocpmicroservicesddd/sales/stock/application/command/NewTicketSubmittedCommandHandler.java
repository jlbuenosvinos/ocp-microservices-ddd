package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class NewTicketSubmittedCommandHandler implements  CommandHandler {

    public static final Logger logger = LoggerFactory.getLogger(NewTicketSubmittedCommandHandler.class);

    /**
     * Executes the command
     * @param command command to be executed
     */
    @Override
    public void execute(Command command) {

    }

}
