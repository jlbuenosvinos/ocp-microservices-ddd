package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketTotalValue;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
public class TicketTotalValueSerializer implements Serializer<TicketTotalValue> {

    public static final Logger logger = LoggerFactory.getLogger(TicketTotalValueSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String topic, TicketTotalValue ticketValue) {
        try {
            return objectMapper.writeValueAsBytes(ticketValue);
        } catch (JsonProcessingException e) {
            logger.error("Unable to serialize object [{}]", ticketValue, e);
            return null;
        }
    }

    @Override
    public void close() {
    }

}
