package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka.ticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEventKey;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
public class TicketGeneratedEventKeySerializer implements Serializer<TicketGeneratedEventKey> {

    public static final Logger logger = LoggerFactory.getLogger(TicketGeneratedEventKeySerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String topic, TicketGeneratedEventKey ticketGeneratedEventKey) {
        try {
            return objectMapper.writeValueAsBytes(ticketGeneratedEventKey);
        } catch (JsonProcessingException e) {
            logger.error("Unable to serialize object [{}]", ticketGeneratedEventKey, e);
            return null;
        }
    }

    @Override
    public void close() {
    }

}
