package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.stream.domain.event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * This class represents a stock event
 * Created by jlbuenosvinos.
 */
public class StockEvent implements DomainEvent {

    private Date occurredOn;
    private String eventId;
    private String eventType;

    private Integer storeId;
    private String productId;
    private Integer units;
    private String eventVersion;

    /**
     * Default constructor
     */
    public StockEvent() {
        super();
        setOccurredOn(new Date());
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }

    public Date getOccurredOn() {
        return occurredOn;
    }

    @Override
    public String getEventId() {
        return this.eventId;
    }

    @Override
    public String getEventType() {
        return this.eventType;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public String getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(String eventVersion) {
        this.eventVersion = eventVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockEvent)) return false;
        StockEvent that = (StockEvent) o;
        return Objects.equals(getOccurredOn(), that.getOccurredOn()) &&
                Objects.equals(getEventId(), that.getEventId()) &&
                Objects.equals(getEventType(), that.getEventType()) &&
                Objects.equals(getStoreId(), that.getStoreId()) &&
                Objects.equals(getProductId(), that.getProductId()) &&
                Objects.equals(getUnits(), that.getUnits());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOccurredOn(), getEventId(), getEventType(), getStoreId(), getProductId(), getUnits());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" eventId: " + getEventId() + NEW_LINE);
        result.append(" eventType: " + getEventType() + NEW_LINE);
        result.append(" occurredOn: " + new SimpleDateFormat("dd/MM/yyyyy HH:mm:ss:SSSSSSS").format(this.occurredOn) + NEW_LINE);
        result.append(" storeId: " + getStoreId() + NEW_LINE);
        result.append(" productId: " + getProductId() + NEW_LINE);
        result.append(" units: " + getUnits() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

    @Override
    public String toJSON() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append("{" + NEW_LINE);
        result.append("    \"store_id\": " + getStoreId() + "," + NEW_LINE);
        result.append("    \"product_id\": \"" + getProductId() + "\","  + NEW_LINE);
        result.append("    \"units\": " + getUnits() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

}
