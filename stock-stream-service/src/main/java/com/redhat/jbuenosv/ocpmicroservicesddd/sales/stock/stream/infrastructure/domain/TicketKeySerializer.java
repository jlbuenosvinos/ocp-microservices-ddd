package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketKey;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
public class TicketKeySerializer implements Serializer<TicketKey> {

    public static final Logger logger = LoggerFactory.getLogger(TicketKeySerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String topic, TicketKey ticketKey) {
        try {
            return objectMapper.writeValueAsBytes(ticketKey);
        } catch (JsonProcessingException e) {
            logger.error("Unable to serialize object [{}]", ticketKey, e);
            return null;
        }
    }

    @Override
    public void close() {
    }

}
