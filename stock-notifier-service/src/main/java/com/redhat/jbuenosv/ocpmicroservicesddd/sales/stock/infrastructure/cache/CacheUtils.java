package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.cache;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import org.infinispan.Cache;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.commons.util.CloseableIterator;
import org.infinispan.commons.util.CloseableIteratorSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class CacheUtils {

    public static final Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    /**
     * Gets the cache keys as a String
     * @param cache
     * @return
     */
    public String cacheKeystoString(RemoteCache cache) {

        Object key = null;
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        if (cache != null) {

            logger.debug("Cache [{}] is NOT NULL and has [{}] entries",cache.getName(),cache.size());

            CloseableIteratorSet iteratorSet = cache.keySet();
            CloseableIterator iterator = iteratorSet.iterator();

            logger.debug("Cache [{}] iterators are ready.",cache.getName());

            try {

                while (iterator.hasNext()) {
                    key = iterator.next();
                    result.append("{" + NEW_LINE);
                    result.append(key.toString());
                    result.append("}");
                }

            }
            catch(Exception e) {
                logger.error("Exception [{}]",e.getMessage());
                throw new StockApplicationException("Unable to get the [" + cache.getName() + "] iterators.");
            }

            return result.toString();

        }
        else {
            throw new StockApplicationException("Cache is null.");
        }

    }

    /**
     * Gets the cache values as a String
     * @param cache
     * @return
     */
    public String cacheKeystoString(Cache cache) {

        StringBuilder result = new StringBuilder();

        if (cache != null) {

        }
        else {
            throw new StockApplicationException("Cache is null.");
        }

        return result.toString();

    }

}
