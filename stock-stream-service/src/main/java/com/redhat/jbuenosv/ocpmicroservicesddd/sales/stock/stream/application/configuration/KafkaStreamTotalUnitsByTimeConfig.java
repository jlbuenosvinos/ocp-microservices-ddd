package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
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
public class KafkaStreamTotalUnitsByTimeConfig {

    public static final Logger logger = LoggerFactory.getLogger(KafkaStreamTotalUnitsByTimeConfig.class);

    @Value("${ticketing.kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${ticketing.kafka.username}")
    private String kafkaUserName;

    @Value("${ticketing.kafka.tickets.topic}")
    private String kafkaTicketsTopicName;

    @Value("${ticketing.kafka.tickets.events.topic}")
    private String kafkaTicketsEventsTopicName;

    public String getKafkaUserName() { return this.kafkaUserName; }

    public String getKafkaTicketsTopicName() {return this.kafkaTicketsTopicName; }

    public String getBootstrapServers() {return this.bootstrapServers; }

    public String getKafkaTicketsEventsTopicName() { return this.kafkaTicketsEventsTopicName; }

    private Properties properties;

    @Bean
    public Map<String,Object> propMapStreamTotalUnitsByTime() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "totalunitsbytime-stream-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "30000");
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "10000");
        return props;
    }

    @Bean
    public Properties propValuesStreamTotalUnitsByTime() {
        Properties props = new Properties();
        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, propMapStreamTotalUnitsByTime().get(StreamsConfig.APPLICATION_ID_CONFIG).toString());
        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, propMapStreamTotalUnitsByTime().get(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG).toString());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, propMapStreamTotalUnitsByTime().get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG).toString());
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, propMapStreamTotalUnitsByTime().get(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG).toString());
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, propMapStreamTotalUnitsByTime().get(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG).toString());
        return props;
    }

    @Bean
    public StreamsConfig streamsFactoryStreamTotalUnitsByTime() {
        return new StreamsConfig(propMapStreamTotalUnitsByTime());
    }

}
