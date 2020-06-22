package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
public class ActiveMQConfig {

    @Value("${ticketing.activemq.broker.url}")
    String brokerUrl;

    @Value("${ticketing.activemq.broker.username}")
    String userName;

    @Value("${ticketing.activemq.broker.password}")
    String password;

    @Value("${ticketing.activemq.tickets.topic}")
    String ticketsTopicName;

    @Value("${ticketing.activemq.store.amq.enabled}")
    Boolean ticketsStoreEnabled;

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getTicketsTopicName() {
        return ticketsTopicName;
    }

    public Boolean isTicketsStoreEnabled() {
        return ticketsStoreEnabled;
    }

}