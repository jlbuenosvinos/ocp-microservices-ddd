package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
public class CommonConfig {

    @Value("${ticketing.event.version}")
    String ticketingEventVersion;

    public String getTicketingEventVersion() {
        return ticketingEventVersion;
    }

}