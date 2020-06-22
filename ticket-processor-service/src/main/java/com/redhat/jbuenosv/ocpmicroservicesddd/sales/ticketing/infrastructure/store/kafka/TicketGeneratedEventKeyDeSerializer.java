package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.store.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event.TicketGeneratedEventKey;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
public class TicketGeneratedEventKeyDeSerializer implements Deserializer<TicketGeneratedEventKey> {

    public static final Logger logger = LoggerFactory.getLogger(TicketGeneratedEventKeyDeSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public TicketGeneratedEventKey deserialize(String s, byte[] bytes) {
        TicketGeneratedEventKey key = null;

        try {
            key = objectMapper.readValue(new String(bytes, "UTF-8"), TicketGeneratedEventKey.class);
        } catch (Exception e) {
            logger.error("Unable to deserialize message [{}]", bytes, e);
        }

        return key;
    }

    @Override
    public void close() {
    }

}
