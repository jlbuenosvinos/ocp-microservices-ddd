package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.command;

/**
 * Created by jlbuenosvinos.
 */
public interface CommandHandler {
    /**
     * Executes the command
     */

    void execute(Command command) ;
}
