package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
public class CommonConfig {

    @Value("${sales.stock.query.service.uri.host}")
    String salesStockQueryUriHost;

    @Value("${sales.stock.query.service.uri.port}")
    Integer salesStockQueryUriPort;

    public String getSalesStockQueryUriHost() {
        return salesStockQueryUriHost;
    }

    public Integer getSalesStockQueryUriPort() {
        return salesStockQueryUriPort;
    }

}
