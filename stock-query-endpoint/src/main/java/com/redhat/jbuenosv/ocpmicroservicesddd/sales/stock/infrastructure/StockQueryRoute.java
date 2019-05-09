package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration.CommonConfig;
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
public class StockQueryRoute extends RouteBuilder {

    public static final Logger logger = LoggerFactory.getLogger(StockQueryRoute.class);

    @Autowired
    CommonConfig config;

    @Override
    public void configure() throws Exception {

        logger.debug("StockQuery Endpoint [{}]", config.getSalesStockQueryUriHost() +  ":" +  config.getSalesStockQueryUriPort());

        /*
        onException(JsonProcessingException.class)
                .handled(true)
                .to("log:Unable to parse the JSON Payload.")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400));

        onException(Exception.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500));
         */

        restConfiguration()
                .component("servlet");

        rest()
                .post("/stock/${storeid}")
                    .produces("application/json")
                    .type(String.class)
                    .to("direct:stock-query-service");

        rest()
                .post("/stock/{storeid}/{productid}")
                    .produces("application/json")
                    .type(String.class)
                    .to("direct:stock-query-service");

        rest()
                .delete("/stock")
                    .to("direct:stock-query-service-delete");

        from("direct:stock-query-service")
                .setHeader(Exchange.HTTP_METHOD,constant(org.apache.camel.component.http4.HttpMethods.GET))
                .to("http4://" + config.getSalesStockQueryUriHost() +  ":" +  config.getSalesStockQueryUriPort()  + "?bridgeEndpoint=true");

        from("direct:stock-query-service")
                .setHeader(Exchange.HTTP_METHOD,constant(org.apache.camel.component.http4.HttpMethods.DELETE))
                .to("http4://" + config.getSalesStockQueryUriHost() +  ":" +  config.getSalesStockQueryUriPort()  + "?bridgeEndpoint=true");

    }

}
