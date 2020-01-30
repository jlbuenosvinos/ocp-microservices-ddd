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

    private KafkaStreams kafkaStreams;
    private KafkaStreams kafkaStreamsTst;

    @PostConstruct
    public void init() {
        logger.debug("begin.");

        StreamsBuilder builder = new StreamsBuilder();

        StreamsBuilder builderTst = new StreamsBuilder();

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

        KStream<TicketKey, TicketTotalValue> eventsTotalSalesStream = eventsTotalSalesKtable.toStream();
        eventsTotalSalesStream.to("tickets-totalsales-topic",Produced.with(ticketKeySerde,ticketTotalValueSerde));
        eventsTotalSalesStream.print(Printed.<TicketKey, TicketTotalValue>toSysOut().withLabel("tickets-totalsales-topic"));

        kafkaStreams = new KafkaStreams(builder.build(),kafkaStreamTotalUnitsByTimeConfig.propValuesStreamTotalUnitsByTime("totalunits-stream-app"));

        KStream<TicketKey, TicketTotalValue> tstStream = builderTst.stream("tickets-totalsales-topic",Consumed.with(ticketKeySerde, ticketTotalValueSerde));
        tstStream.print(Printed.<TicketKey, TicketTotalValue>toSysOut().withLabel("tickets-totalsales-topic"));

        kafkaStreamsTst = new KafkaStreams(builderTst.build(),kafkaStreamTotalUnitsByTimeConfig.propValuesStreamTotalUnitsByTime("read-totalunits-stream-app"));

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

        if (kafkaStreamsTst != null) {
            kafkaStreamsTst.start();
            logger.error("kafkaStreamsTst has been started.");
        }
        else {
            logger.error("kafkaStreamsTst is null");
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
