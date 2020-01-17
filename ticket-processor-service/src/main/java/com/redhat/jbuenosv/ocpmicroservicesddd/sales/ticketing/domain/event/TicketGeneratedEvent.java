package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.model.Ticket;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a ticket event
 * Created by jlbuenosvinos.
 */
@Component
public class TicketGeneratedEvent implements DomainEvent {

    private Date occurredOn;
    private String eventId;
    private String eventType;
    private Ticket ticket;
    private String eventVersion;

    /**
     * Default constructor
     */
    public TicketGeneratedEvent() {
        super();
        setOccurredOn(new Date());
        this.eventVersion = "1.0";
    }

    public String getEventVersion() {
        return eventVersion;
    }

    public void setEventVersion(String eventVersion) {
        this.eventVersion = eventVersion;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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

    @Override
    public String toJSON() {
        return getTicket().toJson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketGeneratedEvent that = (TicketGeneratedEvent) o;

        if (occurredOn != null ? !occurredOn.equals(that.occurredOn) : that.occurredOn != null) return false;
        if (eventId != null ? !eventId.equals(that.eventId) : that.eventId != null) return false;
        if (eventType != null ? !eventType.equals(that.eventType) : that.eventType != null) return false;

        return ticket != null ? ticket.equals(that.ticket) : that.ticket == null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append(" eventId: " + getEventId() + NEW_LINE);
        result.append(" eventType: " + getEventType() + NEW_LINE);
        result.append(" eventVersion: " + getEventVersion() + NEW_LINE);
        result.append(" occurredOn: " + new SimpleDateFormat("dd/MM/yyyyy HH:mm:ss:SSSSSSS").format(this.occurredOn) + NEW_LINE);
        result.append(" ticket: " + this.ticket.toString() + NEW_LINE);
        result.append("}");
        return result.toString();
    }

    @Override
    public int hashCode() {
        int result = occurredOn != null ? occurredOn.hashCode() : 0;
        result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
        result = 31 * result + (eventType != null ? eventType.hashCode() : 0);
        result = 31 * result + (ticket != null ? ticket.hashCode() : 0);
        return result;
    }

}
