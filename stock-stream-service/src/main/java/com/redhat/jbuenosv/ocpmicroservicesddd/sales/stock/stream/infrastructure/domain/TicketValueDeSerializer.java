package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketValue;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
public class TicketValueDeSerializer implements Deserializer<TicketValue> {

    public static final Logger logger = LoggerFactory.getLogger(TicketValueDeSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public TicketValue deserialize(String s, byte[] bytes) {
        TicketValue value = null;
        try {
            value = objectMapper.readValue(new String(bytes, "UTF-8"), TicketValue.class);
        } catch (Exception e) {
            logger.error("Unable to deserialize message [{}]", bytes, e);
        }
        return value;
    }

    @Override
    public void close() {
    }

}
