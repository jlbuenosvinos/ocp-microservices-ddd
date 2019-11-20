package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.CommonConfig;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class OrderRoute extends RouteBuilder {

    public static final Logger logger = LoggerFactory.getLogger(OrderRoute.class);

    @Autowired
    CommonConfig config;

    @Override
    public void configure() throws Exception {

        logger.debug("TicketProcessor Endpoint [{}]", config.getTicketingTicketProcessorUriHost() +  ":" +  config.getTicketingTicketProcessorUriPort());

        onException(JsonProcessingException.class)
                .handled(true)
                .to("log:Unable to parse the JSON Payload.")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400));

        onException(Exception.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500));

        restConfiguration()
                .component("servlet")
                .apiContextPath("/api-doc")
                .apiProperty("Ticket Query API", "Ticket Query API").apiProperty("api.version", "1.0.0");

        rest()
                .post("/order")
                .description("Sends and order to be processed.")
                .type(String.class)
                .outType(String.class)
                .to("direct:ticket-processor-service");

        from("direct:ticket-processor-service")
                .log("direct:ticket-processor-service body [${body}]")
                .setHeader(Exchange.HTTP_METHOD,constant(org.apache.camel.component.http4.HttpMethods.POST))
                .to("http4://" + config.getTicketingTicketProcessorUriHost() +  ":" +  config.getTicketingTicketProcessorUriPort()  + "/api/order?bridgeEndpoint=true");

        rest()
                .post("/health")
                .to("direct:health-service");

        from("direct:health-service")
                .to("http4://" + config.getTicketingTicketProcessorUriHost() +  ":" +  config.getTicketingTicketProcessorUriPort()  + "/api/health?bridgeEndpoint=true");

    }

}
