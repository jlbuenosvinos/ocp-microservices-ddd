package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.listener.StockCacheListener;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.cache.StockCacheFactory;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * This service includes the main logic for the ticketing projection service
 * Created by jlbuenosvinos.
 */
@Service
public class StockServiceImpl implements StockService {

    public static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    private RemoteCache<StockKey,StockValue> cache;

    @Autowired
    StockCacheFactory stockCacheFactory;

    @PostConstruct
    public void init() {
        logger.debug("Service init.");
        cache = stockCacheFactory.getRemoteCache();
        logger.debug("Service init ends.");
    }

    /**
     * register the notification service
     * @throws StockApplicationException
     */
    public void registerNotification() throws StockApplicationException {

        try {

            if (cache != null) {
                cache.addClientListener(new StockCacheListener());
                logger.debug("Stock cache listener registered.");
            }
            else {
                logger.error("Stock cache is not available.");
                throw new StockApplicationException("Stock cache is not available.");
            }

        }
        catch (Exception e) {
            logger.error("Exception [{}]",e.getMessage());
            throw new StockApplicationException(e.getMessage());
        }

    }

    @PreDestroy
    public void stop() {
        try {
            if (cache != null) {
                RemoteCacheManager rc = cache.getRemoteCacheManager();
                if (rc != null) {
                    rc.stop();
                    rc = null;
                    logger.debug("The cache manager has been stop.");
                }
            }
        } // end try
        catch(Exception e) {
            logger.error("Unable to stop the cache manager. [{}]",e.getMessage());
        }
    }

}
