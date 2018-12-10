package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.listener;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.handler.StockCacheHandler;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jlbuenosvinos.
 */
@ClientListener
public class StockCacheListener {

    public static final Logger logger = LoggerFactory.getLogger(StockCacheListener.class);

    @Autowired
    StockCacheHandler handler;

    @ClientCacheEntryCreated
    public void entryCreated(ClientCacheEntryCreatedEvent<String> event) {

        if (event != null) {
            logger.debug("entryCreated event [{}] catched.",event.getKey());


        }
        else {
            logger.error("");
        }

    }

    @ClientCacheEntryModified
    public void entryModified(ClientCacheEntryModifiedEvent<String> event) {
        logger.debug("entryModified event [{}] catched.",event.getKey());


    }

}
