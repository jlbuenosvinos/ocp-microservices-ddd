package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.cache;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration.DataGridConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository.StockKeyMarshaller;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository.StockSecurityCallbackHandler;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository.StockValueMarshaller;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.util.FileUtils;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.configuration.TransactionMode;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.client.hotrod.transaction.lookup.GenericTransactionManagerLookup;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.commons.configuration.XMLStringConfiguration;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class StockRemoteCacheFactory implements CacheFactory {

    public static final Logger logger = LoggerFactory.getLogger(StockRemoteCacheFactory.class);

    private static final String STOCK_CACHE_NAME = "STOCK_TRAN";

    private static final String PROTOBUF_DEFINITION_RESOURCE = "/stock.proto";

    @Autowired
    DataGridConfig config;

    @Override
    public RemoteCache<StockKey,StockValue> getRemoteCache() {

        String errors = null;
        RemoteCacheManager cacheManager;
        RemoteCache<StockKey, StockValue> cache;
        FileUtils fileUtils = new FileUtils();
        SerializationContext ctx = null;

        try {

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.addServer().host(config.getServerUrl()).port(config.getServerPort());

            logger.debug("Added server details.");

            builder.security()
                    .authentication()
                    .serverName("jdg-server")
                    .saslMechanism("DIGEST-MD5")
                    .callbackHandler(new StockSecurityCallbackHandler(config.getUserName(),config.getPassword(),"ApplicationRealm"))
                    .enable();

            logger.debug("Configuration security.");

            builder.marshaller(new ProtoStreamMarshaller());
            cacheManager = new RemoteCacheManager(builder.build());

            XMLStringConfiguration xml = new XMLStringConfiguration(String.format(
                    "<infinispan>" +
                            "<cache-container>" +
                                "<distributed-cache name=\"%1$s\">" +
                                    "<locking isolation=\"REPEATABLE_READ\" striping=\"false\"/>" +
                                    "<transaction mode=\"NON_XA\" locking=\"PESSIMISTIC\" protocol=\"DEFAULT\" transaction-manager-lookup=\"org.infinispan.transaction.lookup.GenericTransactionManagerLookup\"/>" +
                                "</distributed-cache>" +
                            "</cache-container>" +
                    "</infinispan>",
                    STOCK_CACHE_NAME
            ));

            cache = cacheManager.administration()
                    .withFlags(CacheContainerAdmin.AdminFlag.PERMANENT)
                    .getOrCreateCache(STOCK_CACHE_NAME, xml);

            if (cache != null) {

                ctx = ProtoStreamMarshaller.getSerializationContext(cacheManager);
                ctx.registerProtoFiles(FileDescriptorSource.fromResources(PROTOBUF_DEFINITION_RESOURCE));
                ctx.registerMarshaller(new StockKeyMarshaller());
                ctx.registerMarshaller(new StockValueMarshaller());

                RemoteCache<String, String> metadataCache = cacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
                metadataCache.put(PROTOBUF_DEFINITION_RESOURCE, fileUtils.readResource(PROTOBUF_DEFINITION_RESOURCE));

                errors = metadataCache.get(ProtobufMetadataManagerConstants.ERRORS_KEY_SUFFIX);

                if (errors != null) {
                    logger.error("Unable to register the Protobuf schema context due to [{}]",errors);
                    throw new StockApplicationException("Unable to register the Protobuf schema context.");
                }

                logger.debug("Protobuf schema have been registered.");
                logger.debug("[{}] transactional cache has been created.",STOCK_CACHE_NAME);

            }
            else {
                logger.error("Unable to get a reference to cache [{}]",STOCK_CACHE_NAME);
                throw new StockApplicationException("Unable to get the ["  + STOCK_CACHE_NAME + "].");
            }

        }
        catch(IOException e) {
            logger.error("Unable to register the Protobuf schema context due to [{}]",e.getMessage());
            throw new StockApplicationException("Unable to register the Protobuf schema context.");
        }
        catch(Exception e) {
            logger.error("Unable to get the [{}] reference due to [{}]",STOCK_CACHE_NAME,e.getMessage());
            throw new StockApplicationException("Unable to get the ["  + STOCK_CACHE_NAME + "] reference due to [" + e.getMessage() + "].");
        }

        return cache;

    }

}
