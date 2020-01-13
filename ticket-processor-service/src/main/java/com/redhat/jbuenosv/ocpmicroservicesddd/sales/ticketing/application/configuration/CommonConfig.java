package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
public class CommonConfig {

    @Value("${ticketing.stock.processor.service.uri}")
    String ticketingStockProcessorUri;

    @Value("${ticketing.event.version}")
    String ticketingEventVersion;

    @Value("${ticketing.servicemesh.on}")
    Boolean isServiceMesh;

    @Value("${ticketing.kafka.store.enabled}")
    Boolean isKafkaStore;

    @Value("${ticketing.sales.vitamine.on}")
    Boolean isSaleVitamine;

    public String getTicketingEventVersion() {
        return ticketingEventVersion;
    }

    public Boolean isServiceMesh() {
        return isServiceMesh;
    }

    public Boolean isKakfaStore() { return isKafkaStore; }

    public String getTicketingStockProcessorUri() {
        return ticketingStockProcessorUri;
    }

    public Boolean isSaleViatamine() {
        return isSaleVitamine;
    }

}