package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception;

/**
 * Created by jlbuenosvinos
 */
public class TicketApplicationException extends RuntimeException {

    public TicketApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketApplicationException(String message) {
        super(message);
    }

    public TicketApplicationException(Throwable cause) {
        super(cause);
    }

}