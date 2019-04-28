package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception;

import org.springframework.stereotype.Component;

@Component("routeException")
public class RouteException extends Exception {

    public RouteException() {
    }

    public RouteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouteException(String message) {
        super(message);
    }

    public RouteException(Throwable cause) {
        super(cause);
    }

}
