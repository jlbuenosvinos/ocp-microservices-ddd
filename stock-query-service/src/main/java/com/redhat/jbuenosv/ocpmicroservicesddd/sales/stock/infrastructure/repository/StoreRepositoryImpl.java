package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration.DataGridConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StoreValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.cache.CacheUtils;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.cache.StockCacheFactory;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Expression;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StoreRepositoryImpl implements StoreRepository {

    public static final Logger logger = LoggerFactory.getLogger(StoreRepositoryImpl.class);

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
     * Gets the stores list
     * @return stores list
     */
    public List<StoreValue> findAll() {

        List<StoreValue> storeValueList = new ArrayList<StoreValue>();
        StoreValue storeValue = new StoreValue();
        Integer currentStoreId = 0;


        if (cache != null) {

            QueryFactory qf = Search.getQueryFactory(cache);
            Query query = qf.from(StockValue.class)
                                .select(Expression.property("storeId"))
                                .groupBy("storeId")
                                .orderBy("storeId")
                            .build();

            List<Object[]> storeValueSearchResult = query.list();
            Integer storeValuesSize = query.getResultSize();

            logger.debug("Query [{}] size [{}]",qf.toString(),storeValuesSize);

            for(int i = 0 ; i < storeValuesSize ; i ++) {
                storeValue = new StoreValue();
                currentStoreId = (Integer)(storeValueSearchResult.get(i))[0];
                storeValue.setStoreId(currentStoreId);
                storeValue.setStoreName("Store[" + currentStoreId + "]");
                storeValueList.add(storeValue);
                logger.debug(storeValue.toString());
            }

        }
        else {
            logger.error("Stock cache is not available.");
            throw new StockApplicationException("Stock cache is not available.");
        } // end else

        return storeValueList;
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
