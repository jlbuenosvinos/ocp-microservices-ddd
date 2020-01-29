package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.model;

import java.io.Serializable;

/**
 * This class represents a ticket event
 * Created by jlbuenosvinos.
 */
public class TicketKey implements Serializable {

    private Integer storeId;
    private String ticketId;

    /**
     * Default constructor
     */
    public TicketKey() {
        this.storeId = 0;
        this.ticketId = "";
    }

    /**
     * Custom constructor
     * @param storeId store id
     * @param ticketId ticket id
     */
    public TicketKey(Integer storeId, String ticketId) {
        this.storeId = storeId;
        this.ticketId = ticketId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketKey ticket = (TicketKey)o;
        if (ticket.getTicketId().equalsIgnoreCase(ticketId) && ticket.getStoreId().intValue() == storeId.intValue()) return true;
        else return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" storeId: " + getStoreId() + NEW_LINE);
        result.append(" ticketId: " + getTicketId() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

    /**
     * Gets the JSON representation
     * @return ticket json representation
     */
    public String toJson() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append("{" + NEW_LINE);
        result.append("    \"store_id\": " + getStoreId() + "," + NEW_LINE);
        result.append("    \"ticket_id\": \"" + getTicketId() + "\","  + NEW_LINE);
        result.append("}");
        return result.toString();
    }

}
