package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.command;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.configuration.CommonConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.event.StockEvent;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.Ticket;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.cache.StockRemoteCacheFactory;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.util.UUIDGenerator;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class NewTicketSubmittedCommandHandler implements  CommandHandler {

    public static final Logger logger = LoggerFactory.getLogger(NewTicketSubmittedCommandHandler.class);

    private static final String STOCK_CACHE_NAME = "STOCK";
    private RemoteCache<StockKey, StockValue> cache;

    @Autowired
    NewTicketSubmittedCommand newTicketSubmittedCommand;

    @Autowired
    UUIDGenerator uuidGenerator;

    @Autowired
    CommonConfig config;

    @Autowired
    StockRemoteCacheFactory stockRemoteCacheFactory;

    @PostConstruct
    public void init() {
        logger.debug("Service init.");
        cache = stockRemoteCacheFactory.getRemoteCache();
        logger.debug("Trans Cache [{}] loaded.",cache.getName());
        logger.debug("Service init ends.");
    }

    /**
     * Executes the command
     * @param command command to be executed
     */
    @Override
    public void execute(Command command) {
        newTicketSubmittedCommand = (NewTicketSubmittedCommand)command;
        Ticket ticket = newTicketSubmittedCommand.getTicket();

        StockValue stockValue = null;
        StockKey stockKey = new StockKey();
        StockValue newStockValue = new StockValue();

        TransactionManager tm = null;

        stockKey.setStoreId(ticket.getStoreId());
        stockKey.setProductId(ticket.getItem().getId());

        StockEvent stockEvent = new StockEvent();
        stockEvent.setEventId(uuidGenerator.getUuid());
        stockEvent.setEventType(StockEvent.class.getName());
        stockEvent.setEventVersion(config.getStockEventVersion());
        stockEvent.setProductId(ticket.getItem().getId());
        stockEvent.setStoreId(ticket.getStoreId());
        stockEvent.setUnits(ticket.getItem().getUnits());

        if (cache != null) {

            stockKey.setStoreId(ticket.getStoreId());
            stockKey.setProductId(ticket.getItem().getId());

            try {

                tm = cache.getTransactionManager();

                if (tm != null) {
                    logger.debug("TransactionManager status [{}].",tm.getStatus());
                    tm.begin();
                    logger.debug("Transaction has been started.");
                }
                else {
                    logger.debug("Transaction Manager is null.");
                }

                stockValue = (StockValue)cache.get(stockKey);

                if (stockValue == null) {
                    newStockValue.setProductId(ticket.getItem().getId());
                    newStockValue.setStoreId(ticket.getStoreId());
                    newStockValue.setUnits(ticket.getItem().getUnits());
                    logger.debug("A new stock entry [{}] has been added [{}].",stockKey,newStockValue.toString());
                }
                else {
                    newStockValue.setUnits(stockValue.getUnits() + ticket.getItem().getUnits());
                    newStockValue.setProductId(stockValue.getProductId());
                    newStockValue.setStoreId(stockValue.getStoreId());
                    logger.debug("An existing stock entry [{}] has been updated resulting in [{}].",stockKey,newStockValue.toString());
                }

                cache.put(stockKey,newStockValue);

                if (tm != null) {
                    tm.commit();
                    logger.debug("The stock entry [{}] related transaction has been committed.",newStockValue.toString());
                }

            }
            catch(Exception e) {
                logger.error("Unable to update the stock [{}].",e.getMessage());
                if (tm != null) {
                    try {
                        tm.rollback();
                        logger.debug("The stock entry [{}] related transaction has been rolled back.",stockKey);
                    }
                    catch (SystemException ex) {
                        logger.error("Unable to rollback the transaction [{}]",ex.getMessage());
                    }
                }
                else {
                    logger.error("The Transaction Manager is null.");
                }
                throw new StockApplicationException("Unable to update the stock.");
            }

        }
        else {
            logger.error("DataGrid stock cache is not available.");
            throw new StockApplicationException("DataGrid stock cache is not available.");
        } // end else

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
