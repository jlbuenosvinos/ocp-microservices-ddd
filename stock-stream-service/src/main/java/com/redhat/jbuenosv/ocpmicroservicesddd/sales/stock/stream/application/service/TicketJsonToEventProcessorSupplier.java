package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketValue;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.kstream.TransformerSupplier;

/**
 * Created by jlbuenosvinos.
 */
public class TicketJsonToEventProcessorSupplier implements TransformerSupplier<String, String, KeyValue<TicketKey, TicketValue>> {

    @Override
    public Transformer<String, String, KeyValue<TicketKey, TicketValue>> get() {
        return new TicketJsonToEventTransformer();
    }

}
