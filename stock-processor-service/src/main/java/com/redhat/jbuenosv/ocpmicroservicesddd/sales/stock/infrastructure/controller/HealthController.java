package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jlbuenosvinos.
 */
@Controller
@RequestMapping("/api")
class HealthController {

    public static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    /**
     * Default constructor
     */
    public HealthController() {
    }

    /**
     * health signal
     * @return HTTP 200 CODE
     */

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity health() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
