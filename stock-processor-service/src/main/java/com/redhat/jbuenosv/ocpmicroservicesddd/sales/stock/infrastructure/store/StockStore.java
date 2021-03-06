package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.store;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration.DataGridConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.event.StockEvent;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.cache.StockCacheFactory;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.google.common.eventbus.Subscribe;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockStore {

    public static final Logger logger = LoggerFactory.getLogger(StockStore.class);

    private static final String STOCK_CACHE_NAME = "STOCK";

    @Autowired
    DataGridConfig config;

    @Autowired
    StockCacheFactory stockCacheFactory;

    private RemoteCache<StockKey, StockValue> cache;

    @PostConstruct
    public void init() {
        logger.debug("Service init.");
        cache = stockCacheFactory.getRemoteCache();
        logger.debug("Service init ends.");
    }

    /**
     * Saves an event to its store
     * @param event event to be stored
     */
    @Subscribe
    public void store(StockEvent event) {
        logger.debug("Saving begin.");

        StockValue stockValue = null;
        StockValue newStockValue = new StockValue();
        StockEvent stockEvent = event;
        StockKey stockKey = new StockKey();

        if (cache != null) {

            stockKey.setStoreId(stockEvent.getStoreId());
            stockKey.setProductId(stockEvent.getProductId());

            logger.debug("stockKey [{}].",stockKey);

            try {
                stockValue = (StockValue)cache.get(stockKey);

                if (stockValue == null) {
                    newStockValue.setProductId(stockEvent.getProductId());
                    newStockValue.setStoreId(stockEvent.getStoreId());
                    newStockValue.setUnits(stockEvent.getUnits());
                    logger.debug("A new stock entry [{}] has been added [{}].",stockKey,newStockValue.toString());
                }
                else {
                    newStockValue.setUnits(stockValue.getUnits() + stockEvent.getUnits());
                    newStockValue.setProductId(stockValue.getProductId());
                    newStockValue.setStoreId(stockValue.getStoreId());
                    logger.debug("An existing stock entry [{}] has been updated resulting in [{}].",stockKey,newStockValue.toString());
                }

                cache.put(stockKey,newStockValue);
            }
            catch(Exception e) {
                logger.error("Unable to update the stock [{}].",e.getMessage());
                throw new StockApplicationException("Unable to update the stock.");
            }

        }
        else {
            logger.error("DataGrid stock cache is not available.");
            throw new StockApplicationException("DataGrid stock cache is not available.");
        } // end else

        logger.debug("Saving end.");
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
