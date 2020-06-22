package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model;

import java.io.Serializable;

/**
 * Enumeration with the different types of Order line size.
 */
public enum OrderLineSize implements Serializable {

    XS,
    S,
    M,
    L,
    X,
    XL;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(name() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

}
