package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by jlbuenosvinos.
 */
@Service
public class TicketStreamLoaderImp implements StreamLoader {

    public static final Logger logger = LoggerFactory.getLogger(TicketStreamLoaderImp.class);

    @PostConstruct
    public void init() {
        logger.debug("TicketStreamLoaderImp init.");

        logger.debug("TicketStreamLoaderImp init ends.");
    }

    @PreDestroy
    public void stop() {
        logger.debug("TicketStreamLoaderImp stop.");

        logger.debug("TicketStreamLoaderImp stop ends.");
    }

    @Override
    public void loadStream() {
        

    }
}
