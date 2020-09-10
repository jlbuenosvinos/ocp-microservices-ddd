package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model;

import java.io.Serializable;

/**
 * This class represents a physical store order line.
 * Created by jlbuenosvinos.
 */
public class OrderLine implements Serializable {

    private String id;
    private String name;
    private OrderLineSize size;
    private Integer units;
    private OrderLineType type;

    /**
     * Defualt constructor
     */
    public OrderLine() {
    }

    /**
     * OrderLine constructor
     * @param id product id
     * @param name product name
     * @param size product size
     * @param units number of units
     * @param type sell or return operation type
     */
    public OrderLine(String id, String name, OrderLineSize size, Integer units, OrderLineType type) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.units = units;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderLineSize getSize() {
        return size;
    }

    public void setSize(OrderLineSize size) {
        this.size = size;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public OrderLineType getType() {
        return type;
    }

    public void setType(OrderLineType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLine orderLine = (OrderLine) o;

        if (id != null ? !id.equals(orderLine.id) : orderLine.id != null) return false;
        if (name != null ? !name.equals(orderLine.name) : orderLine.name != null) return false;
        if (size != orderLine.size) return false;
        if (units != null ? !units.equals(orderLine.units) : orderLine.units != null) return false;
        return type == orderLine.type;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (units != null ? units.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" id: " + getId() + NEW_LINE);
        result.append(" name: " + getName() + NEW_LINE);
        result.append(" size: " + getSize() + NEW_LINE);
        result.append(" units: " + getUnits() + NEW_LINE);
        result.append(" type: " + getType() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

    /**
     * Gets the JSON representation
     * @return order line json representation
     */
    public String toJson() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append("     {" + NEW_LINE);
        result.append("         \"id\": " + getId() + "," + NEW_LINE);
        result.append("         \"name\": \"" + getName() + "\","  + NEW_LINE);
        result.append("         \"size\": \"" +  getSize().name() + "\","  + NEW_LINE);
        result.append("         \"units\": " + getUnits() + "," + NEW_LINE);
        result.append("         \"operation_type\": \"" +  getType().name() + "\"" + NEW_LINE);
        result.append("     }");
        return result.toString();
    }

}
