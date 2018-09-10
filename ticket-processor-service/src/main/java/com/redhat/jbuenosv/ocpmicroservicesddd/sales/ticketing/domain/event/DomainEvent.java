package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.domain.event;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jlbuenosvinos
 */
public interface DomainEvent extends Serializable {

    /**
     * Gets the version of the event
     * @return
     */
    String getEventVersion();

    /**
     * Gets the event identifier
     * @return event identifier
     */
    String getEventId();

    /**
     * GEts the event type
     * @return event type
     */
    String getEventType();

    /**
     * Gets the domain occurrence date
     * @return domain occurrence date
     */
    Date occurredOn();

    /**
     * Gets the JSON representation
     * @return JSON representation
     */
    String toJSON();

}
