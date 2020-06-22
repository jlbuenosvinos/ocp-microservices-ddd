package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.application.service.processor;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketKey;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model.TicketTotalValue;
import org.apache.kafka.streams.kstream.Predicate;

/**
 * Created by jlbuenosvinos.
 */
public class ReturnPredicate implements Predicate<TicketKey, TicketTotalValue> {

    @Override
    public boolean test(TicketKey ticketKey, TicketTotalValue ticketTotalValue) {
        return ticketTotalValue.getUnits() < 0;
    }
    
}
