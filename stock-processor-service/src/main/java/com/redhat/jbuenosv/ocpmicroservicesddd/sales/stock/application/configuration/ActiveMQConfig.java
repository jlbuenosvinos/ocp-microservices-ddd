package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
@EnableJms
public class ActiveMQConfig {

    @Value("${ticketing.activemq.broker.url}")
    String brokerUrl;

    @Value("${ticketing.activemq.broker.username}")
    String userName;

    @Value("${ticketing.activemq.tickets.topic}")
    String ticketsTopicName;

    @Value("${ticketing.stock.subscriber.client.id}")
    String ticketsSubscriberName;

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getTicketsTopicName() {
        return ticketsTopicName;
    }

    public String getTicketsSubscriberName() { return ticketsSubscriberName; }

    /**
     * Gets the connection listener factory
     * @return the connection listener factory
     */
    @Bean
    public ActiveMQConnectionFactory topicListenerFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(getBrokerUrl());
        connectionFactory.setUserName(getUserName());
        return connectionFactory;
    }

    /**
     * Gets de default connection factory
     * @return the default connection listener factory
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(Boolean.TRUE);
        factory.setSubscriptionDurable(Boolean.TRUE);
        factory.setClientId(getTicketsSubscriberName());
        factory.setConnectionFactory(topicListenerFactory());
        factory.setSessionTransacted(Boolean.TRUE);
        return factory;
    }

}