package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
public class CommonConfig {

    @Value("${stock.event.version}")
    String stockEventVersion;

    public String getStockEventVersion() {
        return stockEventVersion;
    }

}