package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.controller;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.exception.StockApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.application.service.StockService;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StoreValue;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlbuenosvinos.
 */
@RestController
@Api(value="/api",produces ="application/json")
@RequestMapping("/api")
public class StockController {

    public static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    /**
     * Gets the stock
     * @param ucBuilder URI builder
     * @return
     */
    @Timed
    @RequestMapping(value = "/stock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StockValueControllerResponse>> getStock(UriComponentsBuilder ucBuilder) {
        ResponseEntity stockQueryResponse = null;
        List<StockValue> stock = new ArrayList<StockValue>();
        List<StockValueControllerResponse> stockResponse = null;
        StockValueControllerResponseAdapter stockAdapter = null;
        Integer stockSize = 0;

        try {
            stockQueryResponse = new ResponseEntity(stockResponse,HttpStatus.BAD_REQUEST);
        }
        catch(StockApplicationException e) {
            stockQueryResponse = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.debug("Error processing the stock query [{}].",e.getMessage());
        }

        return stockQueryResponse;
    }

    /**
     * Gets the stock by store id
     * @param storeid store id
     * @param ucBuilder URI builder
     * @return
     */
    @Timed
    @RequestMapping(value = "/stock/{storeid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StockValueControllerResponse>> getStock(@PathVariable Integer storeid, UriComponentsBuilder ucBuilder) {
        ResponseEntity stockQueryResponse = null;
        List<StockValue> stock = new ArrayList<StockValue>();
        List<StockValueControllerResponse> stockResponse = null;
        StockValueControllerResponseAdapter stockAdapter = null;
        Integer stockSize = 0;

        try {
            if (storeid != null) {
                // stock must be queried only by store id
                logger.debug("Getting the stock for store [{}]",storeid);

                stock = stockService.getStock(storeid);
                stockSize = stock.size();

                if (stockSize == 0) {
                    logger.debug("No stock for store [{}]",storeid);
                    stockQueryResponse = new ResponseEntity(HttpStatus.NOT_FOUND);
                }
                else {
                    logger.debug("[{}] stock entries  for store [{}]",stock.size(),storeid);

                    stockResponse = new ArrayList<StockValueControllerResponse>(stockSize);
                    for(int i = 0 ; i < stockSize ; i ++) {
                        stockAdapter = new StockValueControllerResponseAdapter(stock.get(i));
                        stockResponse.add(stockAdapter.getStockValueControllerResponse());
                    }
                    stockQueryResponse = new ResponseEntity(stockResponse,HttpStatus.OK);
                }
            }
            else {
                stockQueryResponse = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }
        }
        catch(StockApplicationException e) {
            stockQueryResponse = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.debug("Error processing the stock query [{}].",e.getMessage());
        }

        return stockQueryResponse;
    }

    /**
     * Gets the product stock in a specific store
     * @param storeid store id
     * @param productid product id
     * @param ucBuilder URI builder
     * @return
     */
    @Timed
    @RequestMapping(value = "/stock/{storeid}/{productid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockValueControllerResponse> getStock(@PathVariable Integer storeid, @PathVariable String productid, UriComponentsBuilder ucBuilder) {
        ResponseEntity stockQueryResponse = null;
        StockValue stock = null;

        try {
            logger.debug("begins [{}][{}]",storeid,productid);
            if (storeid != null) {
                if (productid != null) {
                    // stock must be queried by store id and product id
                    stock = stockService.getStock(storeid, productid);

                    if (stock == null) {
                        logger.debug("No stock for store and product [{},{}]",storeid,productid);
                        stockQueryResponse = new ResponseEntity(HttpStatus.NOT_FOUND);
                    }
                    else {
                        logger.debug("A stock entry has been found for store [{},{}]",storeid,productid);
                        stockQueryResponse = new ResponseEntity(new StockValueControllerResponseAdapter(stock).getStockValueControllerResponse(),HttpStatus.OK);
                    }
                }
            }
            else {
                stockQueryResponse = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }
        }
        catch(StockApplicationException e) {
            stockQueryResponse = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.debug("Error processing the stock query [{}].",e.getMessage());
        }

        return stockQueryResponse;

    }

    /**
     * Gets the stores list
     * @param ucBuilder URI builder
     * @return
     */
    @Timed
    @RequestMapping(value = "/store", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StoreValueControllerResponse>> getStores(UriComponentsBuilder ucBuilder) {
        ResponseEntity storeQueryResponse = null;
        List<StoreValue> stores = new ArrayList<StoreValue>();
        List<StoreValueControllerResponse> storeResponse = null;
        StoreValueControllerResponseAdapter storeAdapter = null;
        Integer storesSize = 0;

        try {

                stores = stockService.getStores();
                storesSize = stores.size();

                if (storesSize == 0) {
                    logger.debug("No stores found.");
                    storeQueryResponse = new ResponseEntity(HttpStatus.NOT_FOUND);
                }
                else {
                    storeResponse = new ArrayList<StoreValueControllerResponse>(storesSize);
                    for(int i = 0 ; i < storesSize ; i ++) {
                        storeAdapter = new StoreValueControllerResponseAdapter(stores.get(i));
                        storeResponse.add(storeAdapter.getStoreValueControllerResponse());
                    }
                    storeQueryResponse = new ResponseEntity(storeResponse,HttpStatus.OK);
                }

        }
        catch(StockApplicationException e) {
            storeQueryResponse = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            logger.debug("Error processing the stores query.",e.getMessage());
        }

        return storeQueryResponse;
    }

}
