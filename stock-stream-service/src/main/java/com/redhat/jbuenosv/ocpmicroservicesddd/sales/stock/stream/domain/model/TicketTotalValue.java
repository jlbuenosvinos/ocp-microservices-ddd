package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a ticket event
 * Created by jlbuenosvinos.
 */
public class TicketTotalValue implements Serializable  {

    private Integer storeId;
    private String id;
    private Long units;

    /**
     * Default constructor
     */
    public TicketTotalValue() {
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

    public Long getUnits() {
        return units;
    }

    public void setUnits(Long units) {
        this.units = units;
    }

    public static TicketTotalValue sum(TicketTotalValue value1, TicketTotalValue value2) {
        TicketTotalValue sumTotal = new TicketTotalValue();
        sumTotal.setId(value2.getId());
        sumTotal.setStoreId(value2.getStoreId());
        sumTotal.setUnits(value1.getUnits() + value2.getUnits());
        return sumTotal;
    }

    public static TicketTotalValue build(TicketValue value) {
        TicketTotalValue ticketTotal = new TicketTotalValue();
        ticketTotal.setId(value.getId());
        ticketTotal.setStoreId(value.getStoreId());
        return ticketTotal;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" storeId: " + getStoreId() + NEW_LINE);
        result.append(" id: " + getId() + NEW_LINE);
        result.append(" units: " + getUnits() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketTotalValue that = (TicketTotalValue) o;
        return Objects.equals(storeId, that.storeId) &&
                Objects.equals(id, that.id) &&
                Objects.equals(units, that.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, id, units);
    }

}
