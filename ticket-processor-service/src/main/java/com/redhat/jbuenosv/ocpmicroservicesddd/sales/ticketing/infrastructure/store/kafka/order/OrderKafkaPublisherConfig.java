package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka.order;

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
import org.springframework.transaction.annotation.Transactional;

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

    @Value("${ticketing.kafka.orders.commands.topic}")
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
        logger.debug("Topic [{}].",getKafkaOrdersTopicName());
        return props;
    }

    @Bean("order-transaction-manager")
    public KafkaTransactionManager<String, String> transactionManager(ProducerFactory<String, String> producerFactory) {
        return new KafkaTransactionManager<String,String>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, String> orderProducerFactory() {
        logger.debug("Kafka producer factory is ready.");
        DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<String, String>(orderProducerConfigs());
        factory.setTransactionIdPrefix("order-trans-");
        return factory;
    }

    @Bean("order-control-template")
    public KafkaTemplate<String, String> orderKafkaTemplate() {
        logger.debug("Kafka order producer template is ready.");
        return new KafkaTemplate<String, String>(orderProducerFactory());
    }

    /**
     * Publishes an event to a Kafka Topic
     * @param topic topic name
     * @param key order id key
     * @param value event value
     */
    @Transactional("order-transaction-manager")
    public void publish(String topic, String key, String value) {
        KafkaTemplate<String, String> orderKafkaTemplate = null;
        logger.debug("Ready to send Event [{}] to topic [{}].",key,topic);
        orderKafkaTemplate = orderKafkaTemplate();
        logger.debug("Transaction initialization [{}].",orderKafkaTemplate.inTransaction());
        orderKafkaTemplate.send(topic,key,value);
        logger.debug("Order Event [{}] has been sent to topic [{}].",key,topic);
    }

}
