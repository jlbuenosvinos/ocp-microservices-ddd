package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a physical store order.
 * Created by jlbuenosvinos.
 */
public class Order implements Serializable {

    // order identifier
    private String orderId;
    private Integer storeId;
    private List<OrderLine> items;

    /**
     * Default constructor
     */
    public Order() {
      this.orderId="undefined";
        this.items = new ArrayList<OrderLine>();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public List<OrderLine> getItems() {
        return items;
    }

    public void addItem(OrderLine item) {
        this.items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return orderId != null ? orderId.equals(order.orderId) : order.orderId == null;
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" storeID: " + getStoreId() + NEW_LINE);
        result.append(" orderId: " + getOrderId() + NEW_LINE);
        result.append(" items: " + getOrderId() + NEW_LINE);
        for(int i = 0 ; i < getItems().size() ; i++) {
            result.append(" item: " + getItems().get(i).toString());
            if (i == getItems().size() - 1) {
                result.append( NEW_LINE);
            }
            else {
                result.append("," + NEW_LINE);
            }
        }
        result.append("}");
        return result.toString();
    }

    /**
     * Gets the JSON representation
     * @return order json representation
     */
    public String toJson() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append("{" + NEW_LINE);
        result.append(" \"store_id\": " + getStoreId() + "," + NEW_LINE);
        result.append(" \"order_id\": \"" + getOrderId() + "\","  + NEW_LINE);
        result.append(" \"items\": [" + NEW_LINE);
        for(int i = 0 ; i < getItems().size() ; i++) {
            result.append(getItems().get(i).toJson() + NEW_LINE);
        }
        result.append("            ]" + NEW_LINE);
        result.append("}");
        return result.toString();
    }

}
