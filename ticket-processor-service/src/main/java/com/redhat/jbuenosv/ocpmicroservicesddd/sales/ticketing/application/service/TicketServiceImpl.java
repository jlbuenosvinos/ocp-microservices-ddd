package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.CommonConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.domain.TicketProcessorEventBus;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.domain.event.DomainEventPublisher;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Order;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.OrderLineType;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Ticket;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.TicketItem;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.TicketStore;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.util.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * This service includes the main logic for the ticketing system.
 * Created by jlbuenosvinos.
 */
@Service
class TicketServiceImpl implements TicketService {

    public static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    TicketStore ticketStore;

    @Autowired
    DomainEventPublisher eventPublisher;

    @Autowired
    TicketProcessorEventBus ticketProcessorEventBus;

    @Autowired
    UUIDGenerator uuidGenerator;

    @Autowired
    CommonConfig config;

    @PostConstruct
    public void init() {
        logger.debug("TicketServiceImpl init.");
        ticketProcessorEventBus.register(this.ticketStore);
        logger.debug("TicketServiceImpl init ends.");
    }

    @PreDestroy
    public void stop() {
        logger.debug("TicketServiceImpl stop.");
        ticketProcessorEventBus.unregister(this.ticketStore);
        logger.debug("TicketServiceImpl stop ends.");
    }

    @Override
    public void processOrder(Order order) {
        Ticket ticket = null;
        TicketItem ticketItem = null;
        int numTickets = order.getItems().size();
        TicketGeneratedEvent ticketEvent = null;
        List<TicketGeneratedEvent> ticketEvents = new ArrayList<TicketGeneratedEvent>();

        for(int i = 0 ; i < numTickets ; i++) {
            ticketEvent = new TicketGeneratedEvent();
            ticketEvent.setEventId(uuidGenerator.getUuid());
            ticketEvent.setEventType(TicketGeneratedEvent.class.getName());
            ticket = new Ticket();
            ticket.setStoreId(order.getStoreId());
            ticket.setTicketId(uuidGenerator.getUuid());
            ticketItem = new TicketItem();
            ticketItem.setId(order.getItems().get(i).getId());

            if (order.getItems().get(i).getType() == OrderLineType.RETURN) {
                ticketItem.setUnits(order.getItems().get(i).getUnits());
            }
            else {
                ticketItem.setUnits(order.getItems().get(i).getUnits() * -1);
            }

            ticket.setItem(ticketItem);
            ticketEvent.setTicket(ticket);
            logger.debug("Added new Ticket: {}",ticket.toString());
            ticketEvent.setEventVersion(config.getTicketingEventVersion());
            ticketEvents.add(ticketEvent);

            ticketProcessorEventBus.post(ticketEvent);
            logger.debug("Ticket [{}] has been post.",ticketEvent.getEventId());

        }

        logger.debug("Order [{}] has been processed.",order.getOrderId());
    }

}
