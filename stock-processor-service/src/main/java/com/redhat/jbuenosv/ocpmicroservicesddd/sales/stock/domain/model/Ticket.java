package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model;

import java.io.Serializable;

/**
 * This class represents a ticketing element
 * Created by jlbuenosvinos.
 */
public class Ticket implements Serializable {

    private Integer storeId;
    private String ticketId;
    private TicketItem item;

    /**
     * Default constructor
     */
    public Ticket() {
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

    public TicketItem getItem() {
        return item;
    }

    public void setItem(TicketItem item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (storeId != null ? !storeId.equals(ticket.storeId) : ticket.storeId != null) return false;
        return item != null ? item.equals(ticket.item) : ticket.item == null;
    }

    @Override
    public int hashCode() {
        int result = storeId != null ? storeId.hashCode() : 0;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" storeID: " + getStoreId() + NEW_LINE);
        result.append(" item: " + getItem().toString() + NEW_LINE);
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
        result.append("{");
        result.append("    \"store_id\": " + getStoreId() + ",");
        result.append("    \"ticket_id\": " + getTicketId() + ",");
        result.append("    \"item\": {");
        result.append("        \"id\": \"" + getItem().getId() + "\",");
        result.append("        \"units\": " + getItem().getUnits());
        result.append("      }");
        result.append("}");
        return result.toString();
    }

}
