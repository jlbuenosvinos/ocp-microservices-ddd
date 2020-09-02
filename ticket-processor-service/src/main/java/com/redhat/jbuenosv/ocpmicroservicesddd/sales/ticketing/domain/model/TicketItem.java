package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model;

import java.io.Serializable;

/**
 * This class represents a ticketing line item
 * Created by jlbuenosvinos.
 */
public class TicketItem implements Serializable {

    private String id;
    private Integer units;

    /**
     * Default constructor
     */
    public TicketItem() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketItem that = (TicketItem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return units != null ? units.equals(that.units) : that.units == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (units != null ? units.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" id: " + getId() + NEW_LINE);
        result.append(" units: " + getUnits() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

}
