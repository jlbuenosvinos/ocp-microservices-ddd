package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jlbuenosvinos.
 */
@Configuration
public class DataGridConfig {

    @Value("${stock.datagrid.server.url}")
    String serverUrl;

    @Value("${stock.datagrid.server.port}")
    String serverPort;

    @Value("${stock.datagrid.username}")
    String userName;

    @Value("${stock.datagrid.password}")
    String password;

    public String getServerUrl() {
        return serverUrl;
    }

    public Integer getServerPort() {
        return Integer.parseInt(serverPort);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}