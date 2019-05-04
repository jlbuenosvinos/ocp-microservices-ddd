package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.CommonConfig;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class OrderRoute extends RouteBuilder {

    @Autowired
    CommonConfig config;

    @Override
    public void configure() throws Exception {

        onException(JsonProcessingException.class)
                .handled(true)
                .to("log:Unable to parse the JSON Payload.")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400));

        onException(Exception.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500));

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);

        rest()
                .post("/order")
                .to("direct:ticket-processor-service");

        from("direct:ticket-processor-service")
                .log("${body}")
                .to("undertow:" + config.getTicketingTicketProcessorUri());

    }

}
