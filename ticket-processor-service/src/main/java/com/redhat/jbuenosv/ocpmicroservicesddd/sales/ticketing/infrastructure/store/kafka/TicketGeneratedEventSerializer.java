package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEvent;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
public class TicketGeneratedEventSerializer implements Serializer<TicketGeneratedEvent> {

    public static final Logger logger = LoggerFactory.getLogger(TicketGeneratedEventSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String s, TicketGeneratedEvent ticketGeneratedEvent) {
        try {
            return objectMapper.writeValueAsBytes(ticketGeneratedEvent);
        } catch (JsonProcessingException e) {
            logger.error("Unable to serialize object [{}]", ticketGeneratedEvent, e);
            return null;
        }
    }

    @Override
    public void close() {
    }

}
