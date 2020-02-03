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
public class TotalUnitsStreamLoaderImp implements StreamLoader {

    public static final Logger logger = LoggerFactory.getLogger(TotalUnitsStreamLoaderImp.class);

    @Autowired
    KafkaStreamConfig kafkaConfig;

    @Autowired
    KafkaStreamTotalUnitsConfig kafkaStreamTotalUnitsByTimeConfig;

    private KafkaStreams kafkaSalesStreams;
    private KafkaStreams kafkaReturnsStreams;

    @PostConstruct
    public void init() {
        logger.debug("begin.");

        StreamsBuilder builderSales = new StreamsBuilder();
        StreamsBuilder builderReturns = new StreamsBuilder();

        Serde<String> stringSerde = Serdes.String();
        Serde<TicketKey> ticketKeySerde = Serdes.serdeFrom(new TicketKeySerializer(), new TicketKeyDeSerializer());
        Serde<TicketValue> ticketValueSerde = Serdes.serdeFrom(new TicketValueSerializer(), new TicketValueDeSerializer());
        Serde<TicketTotalValue> ticketTotalValueSerde = Serdes.serdeFrom(new TicketTotalValueSerializer(), new TicketTotalValueDeSerializer());

        StoreBuilder<KeyValueStore<TicketKey, TicketTotalValue>> keyValueStoreBuilder = Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore("ticketTotalValueState"),ticketKeySerde,ticketTotalValueSerde);
        StoreBuilder<KeyValueStore<TicketKey, TicketTotalValue>> keyValueReturnsStoreBuilder = Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore("ticketTotalReturnsValueState"),ticketKeySerde,ticketTotalValueSerde);
        builderSales.addStateStore(keyValueStoreBuilder);
        builderReturns.addStateStore(keyValueReturnsStoreBuilder);

        KTable<TicketKey, TicketTotalValue> eventsTotalSalesKtable = builderSales.stream(kafkaConfig.getKafkaTicketsEventsTopicName(),
                                                                                         Consumed.with(ticketKeySerde,ticketValueSerde)
                                                                                                 .withOffsetResetPolicy(EARLIEST))
                                                                                                 .mapValues(TicketTotalValue::build)
                                                                                                 .filter(new SalePredicate())
                                                                                                 .groupByKey(Serialized.with(ticketKeySerde, ticketTotalValueSerde))
                                                                                                 .reduce(TicketTotalValue::sum);

        KTable<TicketKey, TicketTotalValue> eventsTotalReturnsKtable = builderReturns.stream(kafkaConfig.getKafkaTicketsEventsTopicName(),
                                                                                             Consumed.with(ticketKeySerde,ticketValueSerde)
                                                                                                     .withOffsetResetPolicy(EARLIEST))
                                                                                                     .mapValues(TicketTotalValue::build)
                                                                                                     .filter(new ReturnPredicate())
                                                                                                     .groupByKey(Serialized.with(ticketKeySerde, ticketTotalValueSerde))
                                                                                                     .reduce(TicketTotalValue::sum);

        KStream<TicketKey, TicketTotalValue> eventsTotalSalesStream = eventsTotalSalesKtable.toStream();
        eventsTotalSalesStream.to("tickets-totalsales-topic",Produced.with(ticketKeySerde,ticketTotalValueSerde));
        eventsTotalSalesStream.print(Printed.<TicketKey, TicketTotalValue>toSysOut().withLabel("tickets-totalsales-topic"));

        KStream<TicketKey, TicketTotalValue> eventsTotalReturnsStream = eventsTotalReturnsKtable.toStream();
        eventsTotalReturnsStream.to("tickets-totalreturns-topic",Produced.with(ticketKeySerde,ticketTotalValueSerde));
        eventsTotalReturnsStream.print(Printed.<TicketKey, TicketTotalValue>toSysOut().withLabel("tickets-totalreturns-topic"));

        this.kafkaSalesStreams = new KafkaStreams(builderSales.build(),kafkaStreamTotalUnitsByTimeConfig.propValuesStreamTotalUnits("totalunits-stream-app"));
        this.kafkaReturnsStreams = new KafkaStreams(builderReturns.build(),kafkaStreamTotalUnitsByTimeConfig.propValuesStreamTotalUnits("totalunits-returns-stream-app"));

        logger.debug("end.");
    }

    @Override
    public void loadStream() {
        logger.debug("begin.");

        if (kafkaSalesStreams != null) {
            kafkaSalesStreams.start();
            logger.error("kafkaSalesStreams has been started.");
        }
        else {
            logger.error("kafkaSalesStreams is null");
        }

        if (kafkaReturnsStreams != null) {
            kafkaReturnsStreams.start();
            logger.error("kafkaReturnsStreams has been started.");
        }
        else {
            logger.error("kafkaReturnsStreams is null");
        }

        logger.debug("end.");
    }

    @PreDestroy
    public void stop() {
        logger.debug("begin.");

        if (kafkaSalesStreams != null) {
            kafkaSalesStreams.close();
            logger.error("kafkaSalesStreams has been closed.");
        }
        else {
            logger.error("kafkaSalesStreams is null");
        }

        if (kafkaReturnsStreams != null) {
            kafkaReturnsStreams.close();
            logger.error("kafkaReturnsStreams has been closed.");
        }
        else {
            logger.error("kafkaReturnsStreams is null");
        }

        logger.debug("end.");
    }

}
