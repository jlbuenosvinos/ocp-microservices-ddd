package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.route;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class OrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);

        rest()
                .get("/hello")
                .to("direct:hello");

        from("direct:hello")
                .log("Hello World");


        /*
        rest("/student").produces("application/json")
                .get("/hello/{name}")
                .route().transform().simple("Hello ${header.name}, Welcome to JavaOutOfBounds.com")
                .endRest()
                .get("/records/{name}").to("direct:records");

        from("direct:records")
                .process(new Processor() {

                    final AtomicLong counter = new AtomicLong();

                    @Override
                    public void process(Exchange exchange) throws Exception {

                        final String name = exchange.getIn().getHeader("name",String.class);
                        exchange.getIn().setBody(new Student(counter.incrementAndGet(),name,"Camel + SpringBoot"));
                    }
                });
         */

    }

}
