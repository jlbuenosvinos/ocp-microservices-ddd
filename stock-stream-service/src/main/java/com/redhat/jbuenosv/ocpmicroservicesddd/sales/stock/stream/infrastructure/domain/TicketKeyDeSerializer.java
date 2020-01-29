package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketKey;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
public class TicketKeyDeSerializer implements Deserializer<TicketKey> {

    public static final Logger logger = LoggerFactory.getLogger(TicketKeyDeSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public TicketKey deserialize(String s, byte[] bytes) {
        TicketKey key = null;
        try {
            key = objectMapper.readValue(new String(bytes, "UTF-8"), TicketKey.class);
        } catch (Exception e) {
            logger.error("Unable to deserialize message [{}]", bytes, e);
        }
        return key;
    }

    @Override
    public void close() {
    }

}
