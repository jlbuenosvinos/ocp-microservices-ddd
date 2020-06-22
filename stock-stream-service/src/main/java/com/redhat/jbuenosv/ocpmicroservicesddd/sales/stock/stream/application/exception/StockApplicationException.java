package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.exception;

/**
 * Created by jlbuenosvinos
 */
public class StockApplicationException extends RuntimeException {

    public StockApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockApplicationException(String message) {
        super(message);
    }

    public StockApplicationException(Throwable cause) {
        super(cause);
    }

}