package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
public class KafkaConfig {

    @Value("${ticketing.kafka.cluster.url}")
    String kafkaClusterURL;

    @Value("${ticketing.kafka.username}")
    String userName;

    @Value("${ticketing.kafka.password}")
    String password;

    @Value("${ticketing.kafka.tickets.topic}")
    String ticketsTopicName;

    public String getkafkaClusterURL() { return kafkaClusterURL; }

    public String getUserName() { return userName; }

    public String getPassword() { return password; }

    public String getTicketsTopicName() { return ticketsTopicName; }

}