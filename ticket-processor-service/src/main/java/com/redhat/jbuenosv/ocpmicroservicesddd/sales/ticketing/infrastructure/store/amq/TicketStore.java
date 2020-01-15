package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.amq;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.ActiveMQConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.CommonConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.EventStore;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class TicketStore implements EventStore {

    public static final Logger logger = LoggerFactory.getLogger(TicketStore.class);

    @Autowired
    ActiveMQConfig mqConfig;

    @Autowired
    CommonConfig commonConfig;

    /**
     * Saves a ticket event
     * @param event ticket event
     */
    @Subscribe
    public void store(TicketGeneratedEvent event) {
        logger.debug("Saving begin.");

        TicketGeneratedEvent ticketGeneratedEvent = event;
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
                msg.setStringProperty("sales-event-type", ticketGeneratedEvent.getEventType());
                msg.setStringProperty("sales-event-id", ticketGeneratedEvent.getEventId());
                msg.setLongProperty("sales-occuredon", ticketGeneratedEvent.getOccurredOn().getTime());
                msg.setStringProperty("sales-event-version", ticketGeneratedEvent.getEventVersion());
                return msg;
            }
        };

        logger.debug("AMQ message is available.");

        template.send(mqConfig.getTicketsTopicName(), mc);

    }

}
