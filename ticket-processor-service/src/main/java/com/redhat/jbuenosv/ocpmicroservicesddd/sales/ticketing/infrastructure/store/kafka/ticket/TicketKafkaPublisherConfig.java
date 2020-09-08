package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka.ticket;

import java.util.ArrayList;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
@EnableTransactionManagement
public class TicketKafkaPublisherConfig {

    public static final Logger logger = LoggerFactory.getLogger(TicketKafkaPublisherConfig.class);

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
    public Map<String, Object> ticketProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, TicketGeneratedEventKeySerializer.class);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "ticket-processor-service");
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        logger.debug("ticketProducerConfigs Kafka producer configuration is ready.");
        return props;
    }

    @Bean
    public ProducerFactory<TicketGeneratedEventKey, String> ticketProducerFactory() {
        logger.debug("Kafka producer factory is ready.");
        DefaultKafkaProducerFactory<TicketGeneratedEventKey, String> factory = new DefaultKafkaProducerFactory<TicketGeneratedEventKey, String>(ticketProducerConfigs());
        //factory.transactionCapable();
        factory.setTransactionIdPrefix("ticket-trans");
        return factory;
    }

    //@Bean("controlTransactionManager")
    @Bean
    public KafkaTransactionManager<TicketGeneratedEventKey, String> transactionManager(ProducerFactory<TicketGeneratedEventKey, String> producerFactory) {
        return new KafkaTransactionManager<TicketGeneratedEventKey, String>(producerFactory);
    }

    //@Bean("controlTemplate")
    @Bean
    public KafkaTemplate<TicketGeneratedEventKey, String> kafkaTemplate() {
        logger.debug("Kafka producer template is ready.");
        return new KafkaTemplate<TicketGeneratedEventKey, String>(ticketProducerFactory());
    }

    @Bean
    public TicketKafkaPublisher publisher() {
        logger.debug("Kafka producer sender is ready.");
        return new TicketKafkaPublisher();
    }

}
