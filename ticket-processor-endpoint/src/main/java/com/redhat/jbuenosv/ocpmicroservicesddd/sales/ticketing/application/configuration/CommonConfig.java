package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
public class CommonConfig {

    @Value("${ticketing.ticket.processor.service.uri}")
    String ticketingTicketProcessorUri;

    @Value("${ticketing.ticket.processor.service.uri.host}")
    String ticketingTicketProcessorUriHost;

    @Value("${ticketing.ticket.processor.service.uri.port}")
    Integer ticketingTicketProcessorUriPort;

    public String getTicketingTicketProcessorUriHost() {
        return ticketingTicketProcessorUriHost;
    }

    public Integer getTicketingTicketProcessorUriPort() {
        return ticketingTicketProcessorUriPort;
    }

    public String getTicketingTicketProcessorUri() {
        return ticketingTicketProcessorUri;
    }
}
