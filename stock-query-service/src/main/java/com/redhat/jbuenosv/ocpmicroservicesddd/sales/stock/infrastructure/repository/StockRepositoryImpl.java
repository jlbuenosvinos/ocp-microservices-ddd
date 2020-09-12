package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.cache.CacheUtils;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.cache.StockCacheFactory;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration.DataGridConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.dsl.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockRepositoryImpl implements StockRepository {

    public static final Logger logger = LoggerFactory.getLogger(StockRepositoryImpl.class);

    private static final String STOCK_CACHE_NAME = "STOCK";

    private RemoteCache<StockKey,StockValue> cache;

    @Autowired
    DataGridConfig config;

    @Autowired
    CacheUtils cacheUtil;

    @Autowired
    StockCacheFactory stockCacheFactory;

    @PostConstruct
    public void init() {
        logger.debug("Service init.");
        cache = stockCacheFactory.getRemoteCache();
        logger.debug("Service init ends.");
    }

    /**
     * Get the current stock for an specific product and store
     * @param storeId store id
     * @return stock value
     */
    public List<StockValue> findByByStoreId(Integer storeId) {

        List<StockValue> stockValueList = new ArrayList<StockValue>();
        StockValue stockValue = new StockValue();

        if (cache != null) {

            logger.debug("Ready to build the query by store id [{}].",storeId);

            QueryFactory qf = Search.getQueryFactory(cache);
            Query query = qf.from(StockValue.class).having("storeId").eq(storeId).build();

            List stockValueSearchResult = query.list();
            Integer stockValuesSize = stockValueSearchResult.size();

            logger.debug("Query [{}] size [{}]",qf.toString(),stockValueSearchResult.size());

            for(int i = 0 ; i < stockValuesSize ; i ++) {
                stockValueList.add((StockValue) stockValueSearchResult.get(i));
                logger.debug(stockValueSearchResult.get(i).toString());
            }

        }
        else {
            logger.error("Stock cache is not available.");
            throw new StockApplicationException("Stock cache is not available.");
        } // end else

        return stockValueList;
    }

    /**
     * Get the current stock for an specific product and store
     * @param storeId store id
     * @param productId product id
     * @return stock value
     */
    public StockValue findByStoreIdProductId(Integer storeId, String productId) {

        StockValue stockValue = null;
        StockKey stockKey = new StockKey(storeId,productId);

        try {

            if (cache != null) {
                stockValue = (StockValue)cache.get(stockKey);

                if (stockValue != null) {
                    logger.debug("Stock entry found using the key [{},{}].",storeId,productId);
                }
                else {
                    logger.debug("No stock entry found using the key [{},{}].",storeId,productId);
                }

            }
            else {
                logger.error("Unable to get a reference to cache [{}]",STOCK_CACHE_NAME);
            }

        }
        catch(Exception e) {
            logger.error("Error finding the stock entry using the key [{},{}].",storeId,productId);
            throw new StockApplicationException("Error finding the stock entry using the key [" + storeId + "," + productId + "].");
        }

        return stockValue;
    }

    /**
     * Removes the stock
     */
    public void removeStock() {

        try {

            if (cache != null) {
                logger.debug("cache size [{}].",cache.size());
                cache.clear();
                logger.debug("cache size [{}].",cache.size());
            }
            else {
                logger.error("Unable to get a reference to cache [{}]",STOCK_CACHE_NAME);
            }

        }
        catch(Exception e) {
            logger.error("Error removing the stock.");
            throw new StockApplicationException("Error removing the stock.");
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
