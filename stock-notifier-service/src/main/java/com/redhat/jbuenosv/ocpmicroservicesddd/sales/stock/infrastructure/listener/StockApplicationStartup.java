package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.listener;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    public static final Logger logger = LoggerFactory.getLogger(StockApplicationStartup.class);

    @Autowired
    StockService stockService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        logger.debug("onApplicationEvent event catched.");

        try {
            stockService.registerNotification();
            logger.debug("Notification service has been registered.");
        }
        catch (StockApplicationException e) {
            logger.error("Unable to start the service due to [] exception.",e.getMessage());
        }

    }

}
