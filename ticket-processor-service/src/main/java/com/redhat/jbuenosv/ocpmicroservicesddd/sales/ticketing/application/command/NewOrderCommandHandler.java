package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command;

import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class NewOrderCommandHandler implements  CommandHandler {

    /**
     * Executes the command
     *
     * @param command
     */
    @Override
    public void execute(Command command) {
        NewOrderCommand newOrderCommand = (NewOrderCommand)command;






    }
}
