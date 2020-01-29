package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a ticket event
 * Created by jlbuenosvinos.
 */
public class TicketValue implements Serializable  {

    private Integer storeId;
    private String ticketId;
    private String id;
    private Integer units;

    /**
     * Default constructor
     */
    public TicketValue() {
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" storeId: " + getStoreId() + NEW_LINE);
        result.append(" ticketId: " + getTicketId() + NEW_LINE);
        result.append(" id: " + getId() + NEW_LINE);
        result.append(" units: " + getUnits() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketValue that = (TicketValue) o;
        return Objects.equals(storeId, that.storeId) &&
                Objects.equals(ticketId, that.ticketId) &&
                Objects.equals(id, that.id) &&
                Objects.equals(units, that.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, ticketId, id, units);
    }

}
