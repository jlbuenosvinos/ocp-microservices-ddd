package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration.KafkaStreamConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
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

    private Boolean isReady;
    private KafkaStreams kafkaStreams;

    @PostConstruct
    public void init() {
        logger.debug("TicketStreamLoaderImp init.");
        Serde<String> stringSerde = Serdes.String();
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> eventsStream = builder.stream(kafkaConfig.getKafkaTicketsTopicName(), Consumed.with(stringSerde, stringSerde));





        eventsStream.to(kafkaConfig.getKafkaTicketsEventsTopicName(), Produced.with(stringSerde, stringSerde));
        eventsStream.print(Printed.<String, String>toSysOut().withLabel(kafkaConfig.getKafkaTicketsEventsTopicName()));
        kafkaStreams = new KafkaStreams(builder.build(),kafkaConfig.propValues());
        logger.debug("TicketStreamLoaderImp init ends.");
    }

    @Override
    public void loadStream() {
        logger.debug("start.");

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
        logger.debug("TicketStreamLoaderImp stop.");

        if (kafkaStreams != null) {
            kafkaStreams.close();
            logger.error("kafkaStreams has been closed.");
        }
        else {
            logger.error("kafkaStreams is null");
        }

        logger.debug("TicketStreamLoaderImp stop ends.");
    }

}
