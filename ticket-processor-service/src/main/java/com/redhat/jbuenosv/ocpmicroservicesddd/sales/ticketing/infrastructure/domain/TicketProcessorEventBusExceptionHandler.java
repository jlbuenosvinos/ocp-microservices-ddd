package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.domain;

import com.google.common.base.Throwables;
import com.google.common.eventbus.SubscriberExceptionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This component manages the event bus exceptions
 * Created by jlbuenosvinos.
 */
@Component
public class TicketProcessorEventBusExceptionHandler implements com.google.common.eventbus.SubscriberExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(TicketProcessorEventBusExceptionHandler.class);

    @Override
    public void handleException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
        logger.debug("handle exception start.");
        // @todo proper exception handling

        // end @todo
        Throwables.propagate(Throwables.getRootCause(throwable));
    }

}