package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.processor;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration.KafkaStreamConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration.KafkaStreamTotalUnitsByTimeConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.StreamLoader;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.transform.TicketJsonToEventTransformer;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketTotalKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketTotalValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.domain.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.kafka.streams.Topology.AutoOffsetReset.EARLIEST;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by jlbuenosvinos.
 */
@Service
public class TotalUnitsByTimeStreamLoaderImp implements StreamLoader {

    public static final Logger logger = LoggerFactory.getLogger(TotalUnitsByTimeStreamLoaderImp.class);

    @Autowired
    KafkaStreamConfig kafkaConfig;

    @Autowired
    KafkaStreamTotalUnitsByTimeConfig kafkaStreamTotalUnitsByTimeConfig;

    private KafkaStreams kafkaStreams;

    @PostConstruct
    public void init() {
        logger.debug("begin.");

        StreamsBuilder builder = new StreamsBuilder();
        Serde<String> stringSerde = Serdes.String();
        Serde<TicketKey> ticketKeySerde = Serdes.serdeFrom(new TicketKeySerializer(), new TicketKeyDeSerializer());
        Serde<TicketValue> ticketValueSerde = Serdes.serdeFrom(new TicketValueSerializer(), new TicketValueDeSerializer());

        Serde<TicketTotalValue> ticketTotalValueSerde = Serdes.serdeFrom(new TicketTotalValueSerializer(), new TicketTotalValueDeSerializer());

        KStream<TicketKey, TicketValue> eventsStream = builder.stream(kafkaConfig.getKafkaTicketsEventsTopicName(),Consumed.with(ticketKeySerde,ticketValueSerde));

        KTable<TicketKey, TicketTotalValue> eventsTotalStream = builder.stream(kafkaConfig.getKafkaTicketsEventsTopicName(),
                                                                               Consumed.with(ticketKeySerde,ticketValueSerde)
                                                                                            .withOffsetResetPolicy(EARLIEST))
                                                                                            .mapValues(TicketTotalValue::build)
                                                                                            .groupByKey(Serialized.with(ticketKeySerde, ticketTotalValueSerde))
                                                                                            .reduce(TicketTotalValue::sum);

        /*

        KTable<String, ShareVolume> shareVolume = builder.stream(STOCK_TRANSACTIONS_TOPIC,
                Consumed.with(stringSerde, stockTransactionSerde)
                        .withOffsetResetPolicy(EARLIEST))
                .mapValues(st -> ShareVolume.newBuilder(st).build())
                .groupBy((k, v) -> v.getSymbol(), Serialized.with(stringSerde, shareVolumeSerde))
                .reduce(ShareVolume::sum);


        shareVolume.groupBy((k, v) -> KeyValue.pair(v.getIndustry(), v), Serialized.with(stringSerde, shareVolumeSerde))
                .aggregate(() -> fixedQueue,
                        (k, v, agg) -> agg.add(v),
                        (k, v, agg) -> agg.remove(v),
                        Materialized.with(stringSerde, fixedSizePriorityQueueSerde))
                .mapValues(valueMapper)
                .toStream().peek((k, v) -> LOG.info("Stock volume by industry {} {}", k, v))
                .to("stock-volume-by-company", Produced.with(stringSerde, stringSerde));

         */


        /*
        KStream<String, String> eventsStream = builder.stream(kafkaConfig.getKafkaTicketsTopicName(),Consumed.with(stringSerde, stringSerde));
        StoreBuilder<KeyValueStore<TicketKey, TicketValue>> keyValueStoreBuilder = Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore("ticketJsonToEventTransformState"),ticketKeySerde,ticketValueSerde);
        builder.addStateStore(keyValueStoreBuilder);
        KStream<TicketKey, TicketValue> ticketsStream = eventsStream.transform(TicketJsonToEventTransformer::new,"ticketJsonToEventTransformState");
        ticketsStream.to(kafkaConfig.getKafkaTicketsEventsTopicName(), Produced.with(ticketKeySerde,ticketValueSerde));
        ticketsStream.print(Printed.<TicketKey, TicketValue>toSysOut().withLabel(kafkaConfig.getKafkaTicketsEventsTopicName()));
        */

        kafkaStreams = new KafkaStreams(builder.build(),kafkaStreamTotalUnitsByTimeConfig.propValues());
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
