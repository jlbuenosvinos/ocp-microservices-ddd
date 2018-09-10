package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.ActiveMQConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class TicketGeneratedEventStore implements EventStore {

    public static final Logger logger = LoggerFactory.getLogger(TicketGeneratedEventStore.class);

    @Autowired
    ActiveMQConfig mqConfig;

    /**
     * Saves a ticket event
     * @param event ticket event
     * @param <T> event type
     */
    public <T> void save(final T event) {
        logger.debug("Saving begin.");

        TicketGeneratedEvent ticketGeneratedEvent = (TicketGeneratedEvent)event;

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(mqConfig.getBrokerUrl());
        connectionFactory.setPassword(mqConfig.getPassword());
        connectionFactory.setUserName(mqConfig.getUserName());
        connectionFactory.setStatsEnabled(Boolean.TRUE);
        logger.debug("ConnectionFactory available.");
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setDeliveryMode(DeliveryMode.PERSISTENT);
        template.setPubSubDomain(Boolean.TRUE);
        template.setSessionTransacted(Boolean.TRUE);

        logger.debug("JMS template is available.");

        MessageCreator mc = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message msg = session.createTextMessage(ticketGeneratedEvent.getTicket().toJson());
                msg.setStringProperty("sales-event-type",ticketGeneratedEvent.getEventType());
                msg.setStringProperty("sales-event-id",ticketGeneratedEvent.getEventId());
                msg.setLongProperty("sales-occuredon",ticketGeneratedEvent.getOccurredOn().getTime());
                msg.setStringProperty("sales-event-version",ticketGeneratedEvent.getEventVersion());
                return msg;
            }
        };

        logger.debug("AMQ message is available.");

        template.send(mqConfig.getTicketsTopicName(),mc);

    }

}
