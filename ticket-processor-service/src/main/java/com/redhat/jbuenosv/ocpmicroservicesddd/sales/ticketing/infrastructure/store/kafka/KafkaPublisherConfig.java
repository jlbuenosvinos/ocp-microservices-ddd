package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka;

import java.util.HashMap;
import java.util.Map;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEventKey;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
public class KafkaPublisherConfig {

    public static final Logger logger = LoggerFactory.getLogger(KafkaPublisherConfig.class);

    @Value("${ticketing.kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${ticketing.kafka.username}")
    private String kafkaUserName;

    @Value("${ticketing.kafka.tickets.topic}")
    private String kafkaTicketsTopicName;

    public String getKafkaUserName() { return this.kafkaUserName; }

    public String getKafkaTicketsTopicName() { return this.kafkaTicketsTopicName; }

    public String getBootstrapServers() { return this.bootstrapServers; }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, TicketGeneratedEventKeySerializer.class);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "ticket-processor-service");
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        logger.debug("Kafka producer configuration is ready.");
        return props;
    }

    @Bean("payloadTransactionManager")
    KafkaTransactionManager<TicketGeneratedEventKey, String> transactionManager() {
        return new KafkaTransactionManager<TicketGeneratedEventKey, String>(producerFactory());
    }

    @Bean
    public ProducerFactory<TicketGeneratedEventKey, String> producerFactory() {
        logger.debug("Kafka producer factory is ready.");
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<TicketGeneratedEventKey, String> kafkaTemplate() {
        logger.debug("Kafka producer template is ready.");
        return new KafkaTemplate<TicketGeneratedEventKey, String>(producerFactory());
    }

    @Bean
    public KafkaPublisher publisher() {
        logger.debug("Kafka producer sender is ready.");
        return new KafkaPublisher();
    }

}
