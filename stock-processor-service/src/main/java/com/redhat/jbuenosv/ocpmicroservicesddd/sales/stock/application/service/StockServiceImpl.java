package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration.CommonConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.event.StockEvent;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.domain.event.DomainEventPublisher;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.store.StockEventStore;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service includes the main logic for the ticketing projection service
 * Created by jlbuenosvinos.
 */
@Service
public class StockServiceImpl implements StockService {

    public static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    DomainEventPublisher eventPublisher;

    @Autowired
    UUIDGenerator uuidGenerator;

    @Autowired
    CommonConfig config;

    @Autowired
    private StockEventStore stockEventStore;

    /**
     * Projects a ticket
     * @param ticket ticket to be processed
     */
    public void processTicket(Ticket ticket) {
        StockValue stockValue = null;
        StockKey stockKey = new StockKey();
        StockValue newStockValue = new StockValue();

        stockKey.setStoreId(ticket.getStoreId());
        stockKey.setProductId(ticket.getItem().getId());

        StockEvent stockEvent = new StockEvent();
        stockEvent.setEventId(uuidGenerator.getUuid());
        stockEvent.setEventType(StockEvent.class.getName());
        stockEvent.setEventVersion(config.getStockEventVersion());
        stockEvent.setProductId(ticket.getItem().getId());
        stockEvent.setStoreId(ticket.getStoreId());
        stockEvent.setUnits(ticket.getItem().getUnits());

        eventPublisher.publish(stockEvent);
        logger.debug("Ticket [{}] has been processed.",ticket.getTicketId());
    }

}
