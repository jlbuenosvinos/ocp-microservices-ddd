package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.controller.kafka;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.TicketGeneratedEventKey;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableTransactionManagement
public class KafkaReceiverConfig {

    public static final Logger logger = LoggerFactory.getLogger(KafkaReceiverConfig.class);

    @Value("${ticketing.kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${ticketing.kafka.username}")
    private String kafkaUserName;

    @Value("${ticketing.kafka.tickets.topic}")
    private String kafkaTicketsTopicName;

    public String getKafkaUserName() { return this.kafkaUserName; }

    public String getKafkaTicketsTopicName() {return this.kafkaTicketsTopicName; }

    public String getBootstrapServers() {return this.bootstrapServers; }

    @Bean
    public ConsumerFactory<TicketGeneratedEventKey, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, TicketGeneratedEventKeyDeSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "1");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,"read_committed");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaTransactionManager<TicketGeneratedEventKey, String> transactionManager(ProducerFactory<TicketGeneratedEventKey, String> consumerFactory) {
        return new KafkaTransactionManager<TicketGeneratedEventKey, String>(consumerFactory);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<TicketGeneratedEventKey, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<TicketGeneratedEventKey, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
