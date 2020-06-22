package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.store;

/**
 * Created by jlbuenosvinos.
 */
public interface EventStore {

    /**
     * Saves an event to its store
     * @param event event to be stored
     * @param <T> event type
     */
    <T> void save(final T event);

}
