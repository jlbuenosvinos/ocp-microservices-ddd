package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a ticket event
 * Created by jlbuenosvinos.
 */
public class TicketTotalKey implements Serializable  {

    private Integer storeId;
    private String id;

    /**
     * Default constructor
     */
    public TicketTotalKey() {
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static TicketTotalKey build(TicketKey value) {
        TicketTotalKey ticketTotalKey = new TicketTotalKey();
        ticketTotalKey.setId(value.getId());
        ticketTotalKey.setStoreId(value.getStoreId());
        ticketTotalKey.setId(value.getId());
        return ticketTotalKey;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" storeId: " + getStoreId() + NEW_LINE);
        result.append(" id: " + getId() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketTotalKey that = (TicketTotalKey) o;
        return Objects.equals(storeId, that.storeId) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, id);
    }

}
