package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.transform;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration.KafkaStreamConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.StreamLoader;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.domain.TicketKeyDeSerializer;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.domain.TicketKeySerializer;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.domain.TicketValueDeSerializer;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.domain.TicketValueSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by jlbuenosvinos.
 */
@Service
public class TicketStreamLoaderImp implements StreamLoader {

    public static final Logger logger = LoggerFactory.getLogger(TicketStreamLoaderImp.class);

    @Autowired
    KafkaStreamConfig kafkaConfig;

    private KafkaStreams kafkaStreams;

    @PostConstruct
    public void init() {
        logger.debug("begin.");

        StreamsBuilder builder = new StreamsBuilder();
        Serde<String> stringSerde = Serdes.String();
        Serde<TicketKey> ticketKeySerde = Serdes.serdeFrom(new TicketKeySerializer(), new TicketKeyDeSerializer());
        Serde<TicketValue> ticketValueSerde = Serdes.serdeFrom(new TicketValueSerializer(), new TicketValueDeSerializer());
        KStream<String, String> eventsStream = builder.stream(kafkaConfig.getKafkaTicketsTopicName(),Consumed.with(stringSerde, stringSerde));
        StoreBuilder<KeyValueStore<TicketKey, TicketValue>> keyValueStoreBuilder = Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore("ticketJsonToEventTransformState"),ticketKeySerde,ticketValueSerde);
        builder.addStateStore(keyValueStoreBuilder);
        KStream<TicketKey, TicketValue> ticketsStream = eventsStream.transform(TicketJsonToEventTransformer::new,"ticketJsonToEventTransformState");
        ticketsStream.to(kafkaConfig.getKafkaTicketsEventsTopicName(), Produced.with(ticketKeySerde,ticketValueSerde));
        ticketsStream.print(Printed.<TicketKey, TicketValue>toSysOut().withLabel(kafkaConfig.getKafkaTicketsEventsTopicName()));
        kafkaStreams = new KafkaStreams(builder.build(),kafkaConfig.propValues());
        logger.debug("end.");
    }

    @Override
    public void loadStream() {
        logger.debug("begin.");

        if (kafkaStreams != null) {
            kafkaStreams.start();
            logger.error("kafkaStreams has been started.");
        }
        else {
            logger.error("kafkaStreams is null");
        }

        logger.debug("end.");
    }

    @PreDestroy
    public void stop() {
        logger.debug("begin.");

        if (kafkaStreams != null) {
            kafkaStreams.close();
            logger.error("kafkaStreams has been closed.");
        }
        else {
            logger.error("kafkaStreams is null");
        }

        logger.debug("end.");
    }

}
