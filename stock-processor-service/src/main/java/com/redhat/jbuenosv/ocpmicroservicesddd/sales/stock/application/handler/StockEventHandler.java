package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.handler;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.event.StockEvent;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.store.StockEventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockEventHandler implements EventHandler {

    public static final Logger logger = LoggerFactory.getLogger(StockEventHandler.class);

    @Autowired
    StockEventStore stockStore;

    @Override
    public <T> void handle(final T event) {
        StockEvent stockEvent = (StockEvent)event;
        logger.debug("Event [{}] has to be handled.",stockEvent.getEventId());
        stockStore.save(event);
        logger.debug("Event [{}] handled.",stockEvent.getEventId());
    }

}
