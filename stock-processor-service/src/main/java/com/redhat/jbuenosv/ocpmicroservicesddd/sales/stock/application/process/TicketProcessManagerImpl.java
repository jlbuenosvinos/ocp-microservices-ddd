package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.process;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command.NewTicketSubmittedCommand;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command.NewTicketSubmittedCommandHandler;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketProcessManagerImpl implements TicketProcessManager {

    @Autowired
    NewTicketSubmittedCommandHandler newTicketSubmittedCommandHandler;

    @Autowired
    NewTicketSubmittedCommand newTicketSubmittedCommand;

    /**
     * Process the ticket event
     * @param ticket ticket
     */
    @Override
    public void processTicket(Ticket ticket) {
        newTicketSubmittedCommand.setTicket(ticket);
        newTicketSubmittedCommandHandler.execute(newTicketSubmittedCommand);
    }

}
