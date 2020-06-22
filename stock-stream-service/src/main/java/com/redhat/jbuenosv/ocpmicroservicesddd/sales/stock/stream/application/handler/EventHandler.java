package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.handler;

import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public interface EventHandler {

    /**
     * Handles an event
     * @param event
     * @param <T>
     */
    <T> void handle(final T event);

}
