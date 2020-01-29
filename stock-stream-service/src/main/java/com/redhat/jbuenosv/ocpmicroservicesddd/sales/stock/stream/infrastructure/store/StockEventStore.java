package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockEventStore implements EventStore {

    public static final Logger logger = LoggerFactory.getLogger(StockEventStore.class);

    @PostConstruct
    public void init() {
        logger.debug("Service init.");

        logger.debug("Service init ends.");
    }

    /**
     * Saves an event to its store
     * @param event event to be stored
     * @param <T> event type
     */
    public <T> void save(final T event) {
        logger.debug("Saving begin.");

        logger.debug("Saving end.");
    }

    @PreDestroy
    public void stop() {
        logger.debug("Service stop.");

        logger.debug("Service stop ends.");
    }

}