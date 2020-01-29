package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.infrastructure.listener;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.processor.TotalUnitsByTimeStreamLoaderImp;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.transform.TicketStreamLoaderImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockStreamApplicationListener {

    public static final Logger logger = LoggerFactory.getLogger(StockStreamApplicationListener.class);

    @Autowired
    TicketStreamLoaderImp ticketStreamLoader;

    @Autowired
    TotalUnitsByTimeStreamLoaderImp totalUnitsbyTimeStreamLoader;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.debug("start.");
        ticketStreamLoader.loadStream();
        totalUnitsbyTimeStreamLoader.loadStream();
        logger.debug("end.");
    }

}
