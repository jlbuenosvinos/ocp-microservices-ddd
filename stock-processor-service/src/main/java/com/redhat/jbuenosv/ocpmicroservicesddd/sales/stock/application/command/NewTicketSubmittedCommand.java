package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class NewTicketSubmittedCommand implements  Command {

    public static final Logger logger = LoggerFactory.getLogger(NewTicketSubmittedCommand.class);

    private Ticket ticket;

    /**
     * Default constructor
     */
    public NewTicketSubmittedCommand() {
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}
