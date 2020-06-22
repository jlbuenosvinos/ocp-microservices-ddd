package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.domain;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * This component starts the event bus service
 * Created by jlbuenosvinos.
 */
@Component
public class TicketProcessorEventBus {

    public static final Logger logger = LoggerFactory.getLogger(TicketProcessorEventBus.class);

    private EventBus eventBus;

    /**
     * Default constructor
     */
    public TicketProcessorEventBus() {
        //this.eventBus = new EventBus();
        this.eventBus = new EventBus(new TicketProcessorEventBusExceptionHandler());
    }

    /**
     * Post an event
     * @param event Event to be posted
     */
    public void post(Object event) {
        this.eventBus.post(event);
    }

    /**
     * Register an event listener
     * @param event event listener to be registered
     */
    public void register(Object event) {
        this.eventBus.register(event);
    }

    /**
     * Unregister an event listener
     * @param event event listerner to be unregistered
     */
    public void unregister(Object event) {
        this.eventBus.unregister(event);
    }

    @PostConstruct
    public void init() {
        logger.debug("TicketProcessorEventBus init.");
        logger.debug("TicketProcessorEventBus init ends.");
    }

    @PreDestroy
    public void stop() {
        logger.debug("TicketProcessorEventBus stop.");
        logger.debug("TicketProcessorEventBus stop ends.");
    }

}
