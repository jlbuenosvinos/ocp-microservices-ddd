package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.builder.TicketBuilder;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketValue;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class TicketJsonToEventTransformer implements Transformer<String, String, KeyValue<TicketKey, TicketValue>> {

    public static final Logger logger = LoggerFactory.getLogger(TicketJsonToEventTransformer.class);

    @Override
    public void init(ProcessorContext processorContext) {
    }

    @Override
    public KeyValue<TicketKey, TicketValue> transform(String s1, String s2) {
        TicketBuilder builder = new TicketBuilder();
        TicketKey key = builder.buildKey(s2);
        TicketValue value = builder.buildValue(s2);
        return new KeyValue<TicketKey, TicketValue>(key,value);
    }

    @Override
    public void close() {
    }

} // end TicketJsonToEventTransformer
