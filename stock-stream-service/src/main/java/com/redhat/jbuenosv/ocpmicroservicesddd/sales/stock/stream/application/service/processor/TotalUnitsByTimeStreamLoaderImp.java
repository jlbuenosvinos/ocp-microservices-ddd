package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.processor;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration.KafkaStreamConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration.KafkaStreamTotalUnitsByTimeConfig;
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

        StoreBuilder<KeyValueStore<TicketKey, TicketTotalValue>> keyValueStoreBuilder = Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore("ticketTotalValueState"),ticketKeySerde,ticketTotalValueSerde);
        builder.addStateStore(keyValueStoreBuilder);

        KTable<TicketKey, TicketTotalValue> eventsTotalSalesKtable = builder.stream(kafkaConfig.getKafkaTicketsEventsTopicName(),
                                                                               Consumed.with(ticketKeySerde,ticketValueSerde)
                                                                                       .withOffsetResetPolicy(EARLIEST))
                                                                                       .mapValues(TicketTotalValue::build)
                                                                                       .filter(new SalePredicate())
                                                                                       .groupByKey(Serialized.with(ticketKeySerde, ticketTotalValueSerde))
                                                                                       .reduce(TicketTotalValue::sum);

        logger.debug("eventsTotalSalesKtable.");

        KTable<TicketKey, TicketTotalValue> eventsTotalReturnsKtable = builder.stream(kafkaConfig.getKafkaTicketsEventsTopicName(),
                                                                                     Consumed.with(ticketKeySerde,ticketValueSerde)
                                                                                             .withOffsetResetPolicy(EARLIEST))
                                                                                             .mapValues(TicketTotalValue::build)
                                                                                             .filter(new ReturnPredicate())
                                                                                             .groupByKey(Serialized.with(ticketKeySerde, ticketTotalValueSerde))
                                                                                             .reduce(TicketTotalValue::sum);

        logger.debug("eventsTotalReturnsKtable.");

        KStream<TicketKey, TicketTotalValue> eventsTotalSalesStream = eventsTotalSalesKtable.toStream();
        eventsTotalSalesStream.to("tickets-totalsales-topic",Produced.with(ticketKeySerde,ticketTotalValueSerde));
        eventsTotalSalesStream.print(Printed.<TicketKey, TicketTotalValue>toSysOut().withLabel("tickets-totalsales-topic"));

        KStream<TicketKey, TicketTotalValue> eventsTotalReturnsStream = eventsTotalReturnsKtable.toStream();
        eventsTotalReturnsStream.to("tickets-totalreturns-topic",Produced.with(ticketKeySerde,ticketTotalValueSerde));
        eventsTotalReturnsStream.print(Printed.<TicketKey, TicketTotalValue>toSysOut().withLabel("tickets-totalreturns-topic"));

        kafkaStreams = new KafkaStreams(builder.build(),kafkaStreamTotalUnitsByTimeConfig.propValuesStreamTotalUnitsByTime());
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
