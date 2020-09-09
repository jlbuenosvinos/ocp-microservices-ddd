package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.controller.kafka;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
@EnableKafka
@Configuration
@EnableTransactionManagement
public class OrderKafkaReceiverConfig {

    public static final Logger logger = LoggerFactory.getLogger(OrderKafkaReceiverConfig.class);

    @Value("${ticketing.kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${ticketing.kafka.username}")
    private String kafkaUserName;

    @Value("${ticketing.kafka.tickets.topic}")
    private String kafkaTicketsTopicName;

    @Value("${ticketing.kafka.orders.commands.topic}")
    private String kafkaOrdersTopicName;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getKafkaUserName() {
        return kafkaUserName;
    }

    public String getKafkaTicketsTopicName() {
        return kafkaTicketsTopicName;
    }

    public String getKafkaOrdersTopicName() {
        return kafkaOrdersTopicName;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "1");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,"read_committed");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean("order-consumer-factory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    /*
    @Bean("order-consumer-transaction-manager")
    public KafkaTransactionManager<String, String> transactionManager(ConcurrentKafkaListenerContainerFactory<String, String> consumerFactory) {
        return new KafkaTransactionManager<String,String>(consumerFactory);
    }
    */

}
