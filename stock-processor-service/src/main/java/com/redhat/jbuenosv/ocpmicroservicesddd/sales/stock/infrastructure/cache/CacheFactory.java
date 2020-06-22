package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.cache;

import org.infinispan.client.hotrod.RemoteCache;

public interface CacheFactory {

    /**
     * Gets a remote cache reference
     * @return cache reference
     */
    RemoteCache getRemoteCache();

}
