package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.processor;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketTotalValue;
import org.apache.kafka.streams.kstream.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jlbuenosvinos.
 */
public class SalePredicate implements Predicate<TicketKey, TicketTotalValue> {

    public static final Logger logger = LoggerFactory.getLogger(SalePredicate.class);

    @Override
    public boolean test(TicketKey ticketKey, TicketTotalValue ticketTotalValue) {

        if (ticketTotalValue != null) {
            return ticketTotalValue.getUnits() > 0;
        }
        else {
            logger.warn("TicketTotalValue is NULL !!!.");
            return false;
        }

    }

}
