package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
public class TicketGeneratedEventDeSerializer implements Deserializer<TicketGeneratedEvent> {

    public static final Logger logger = LoggerFactory.getLogger(TicketGeneratedEventDeSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public TicketGeneratedEvent deserialize(String s, byte[] bytes) {
        TicketGeneratedEvent event = null;

        try {
            event = objectMapper.readValue(new String(bytes, "UTF-8"), TicketGeneratedEvent.class);
        } catch (Exception e) {
            logger.error("Unable to deserialize message [{}]", bytes, e);
        }

        return event;
    }

    @Override
    public void close() {
    }

}
