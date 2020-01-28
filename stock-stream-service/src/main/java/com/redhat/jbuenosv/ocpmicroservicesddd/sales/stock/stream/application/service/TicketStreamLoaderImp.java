package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration.KafkaStreamConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
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

    @PostConstruct
    public void init() {
        logger.debug("TicketStreamLoaderImp init.");
        logger.debug("TicketStreamLoaderImp init ends.");
    }

    @Override
    public void loadStream() {
        logger.debug("start.");

        Serde<String> stringSerde = Serdes.String();
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> simpleFirstStream = builder.stream(kafkaConfig.getKafkaTicketsTopicName(), Consumed.with(stringSerde, stringSerde));

        //KStream<String, String> upperCasedStream = simpleFirstStream.mapValues(String::toUpperCase);

        simpleFirstStream.to( "my-output-topic", Produced.with(stringSerde, stringSerde));
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(),kafkaConfig.streamsFactory());
        kafkaStreams.start();

        logger.debug("end.");
    }

    @PreDestroy
    public void stop() {
        logger.debug("TicketStreamLoaderImp stop.");
        logger.debug("TicketStreamLoaderImp stop ends.");
    }

}
