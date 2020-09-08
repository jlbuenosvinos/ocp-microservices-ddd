package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka.order;

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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
@EnableTransactionManagement
public class OrderKafkaPublisherConfig {

    public static final Logger logger = LoggerFactory.getLogger(OrderKafkaPublisherConfig.class);

    @Value("${ticketing.kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${ticketing.kafka.username}")
    private String kafkaUserName;

    @Value("${ticketing.kafka.order.commands.topic}")
    private String kafkaOrdersTopicName;

    public String getKafkaUserName() { return this.kafkaUserName; }

    public String getKafkaOrdersTopicName() {
        return kafkaOrdersTopicName;
    }

    public String getBootstrapServers() { return this.bootstrapServers; }

    @Bean
    public Map<String, Object> orderProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "ticket-processor-service-orders");
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        logger.debug("orderProducerConfigs Kafka order producer configuration is ready.");
        return props;
    }

    //@Bean("controlTransactionManager")
    @Bean
    public KafkaTransactionManager<String, String> transactionManager(ProducerFactory<String, String> producerFactory) {
        return new KafkaTransactionManager<String,String>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, String> orderProducerFactory() {
        logger.debug("Kafka producer factory is ready.");
        DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<String, String>(orderProducerConfigs());
        //factory.transactionCapable();
        factory.setTransactionIdPrefix("trans-");
        return factory;
    }

    //@Bean("controlTemplate")
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        logger.debug("Kafka producer template is ready.");
        return new KafkaTemplate<String, String>(orderProducerFactory());
    }

    @Bean
    public OrderKafkaPublisher publisher() {
        logger.debug("Kafka producer sender is ready.");
        return new OrderKafkaPublisher();
    }

}
