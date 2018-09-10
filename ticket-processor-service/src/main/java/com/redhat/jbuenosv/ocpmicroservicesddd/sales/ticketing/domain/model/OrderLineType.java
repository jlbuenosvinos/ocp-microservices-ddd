package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model;

import java.io.Serializable;

/**
 * Enumeration with the different types of Orders
 */
public enum OrderLineType implements Serializable {

    // Regular sale
    SALE,
    // Return
    RETURN

}
