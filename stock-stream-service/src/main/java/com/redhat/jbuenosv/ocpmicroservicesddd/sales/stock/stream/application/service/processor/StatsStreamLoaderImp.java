package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.processor;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration.KafkaStreamConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration.KafkaStreamTotalUnitsConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.StreamLoader;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketTotalValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.domain.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

import org.apache.kafka.streams.kstream.Materialized;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.apache.kafka.streams.Topology.AutoOffsetReset.EARLIEST;

/**
 * Created by jlbuenosvinos.
 */
@Service
public class StatsStreamLoaderImp implements StreamLoader {

    public static final Logger logger = LoggerFactory.getLogger(StatsStreamLoaderImp.class);

    @Autowired
    KafkaStreamConfig kafkaConfig;

    @Autowired
    KafkaStreamTotalUnitsConfig kafkaStreamTotalUnitsByTimeConfig;

    private KafkaStreams kafkaStatsStreams;

    @PostConstruct
    public void init() {
        logger.debug("begin.");

        StreamsBuilder builderStats = new StreamsBuilder();

        Serde<String> stringSerde = Serdes.String();
        Serde<TicketKey> ticketKeySerde = Serdes.serdeFrom(new TicketKeySerializer(), new TicketKeyDeSerializer());
        Serde<TicketValue> ticketValueSerde = Serdes.serdeFrom(new TicketValueSerializer(), new TicketValueDeSerializer());
        Serde<TicketTotalValue> ticketTotalValueSerde = Serdes.serdeFrom(new TicketTotalValueSerializer(), new TicketTotalValueDeSerializer());

        StoreBuilder<KeyValueStore<TicketKey, TicketTotalValue>> statsBuilder = Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore("statsState"),ticketKeySerde,ticketTotalValueSerde);
        builderStats.addStateStore(statsBuilder);

        KTable<TicketKey, TicketTotalValue> eventsTotalSalesKtable = builderStats.stream(kafkaConfig.getKafkaTicketsEventsTopicName(),
                                                                                         Consumed.with(ticketKeySerde,ticketValueSerde)
                                                                                                 .withOffsetResetPolicy(EARLIEST))
                                                                                                 .mapValues(TicketTotalValue::build)
                                                                                                 .filter(new SalePredicate())
                                                                                                 .groupByKey(Serialized.with(ticketKeySerde, ticketTotalValueSerde))
                                                                                                 .reduce(TicketTotalValue::sum);





        this.kafkaStatsStreams = new KafkaStreams(builderStats.build(),kafkaStreamTotalUnitsByTimeConfig.propValuesStreamTotalUnits("stats-stream-app"));

        logger.debug("end.");
    }

    @Override
    public void loadStream() {
        logger.debug("begin.");

        if (kafkaStatsStreams != null) {
            kafkaStatsStreams.start();
            logger.error("kafkaStatsStreams has been started.");
        }
        else {
            logger.error("kafkaStatsStreams is null");
        }

        logger.debug("end.");
    }

    @PreDestroy
    public void stop() {
        logger.debug("begin.");

        if (kafkaStatsStreams != null) {
            kafkaStatsStreams.close();
            logger.error("kafkaStatsStreams has been closed.");
        }
        else {
            logger.error("kafkaStatsStreams is null");
        }

        logger.debug("end.");
    }

}
