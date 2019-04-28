package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.route;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by jlbuenosvinos.
 */
public class OrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        rest("/say/hello")
                .get().route().transform().constant("Hello World");

        rest("/say/bye")
                .get().consumes("application/json").route().transform().constant("Bye World").endRest()
                .post().to("mock:update");
    }

}
