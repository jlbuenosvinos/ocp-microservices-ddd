package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration;

import org.apache.kafka.streams.StreamsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@EnableKafka
@Configuration
public class KafkaStreamConfig {

    public static final Logger logger = LoggerFactory.getLogger(KafkaStreamConfig.class);

    @Value("${ticketing.kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${ticketing.kafka.username}")
    private String kafkaUserName;

    @Value("${ticketing.kafka.tickets.topic}")
    private String kafkaTicketsTopicName;

    public String getKafkaUserName() { return this.kafkaUserName; }

    public String getKafkaTicketsTopicName() {return this.kafkaTicketsTopicName; }

    public String getBootstrapServers() {return this.bootstrapServers; }

    private Properties properties;

    @Bean
    private Map<String,Object> propMap() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-stream-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        return props;
    }

    @Bean
    public Properties propValues() {
        Properties props = new Properties();
        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, propMap().get(StreamsConfig.APPLICATION_ID_CONFIG).toString());
        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, propMap().get(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG).toString());
        return props;
    }

    @Bean
    public StreamsConfig streamsFactory() {
        return new StreamsConfig(propMap());
    }

}
