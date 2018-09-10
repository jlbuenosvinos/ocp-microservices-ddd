package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
@Component
@ConfigurationProperties("events")
public class EventHandlersConfig {

    public static final Logger logger = LoggerFactory.getLogger(EventHandlersConfig.class);

    private List<String> event;
    private List<String> handler;

    public EventHandlersConfig() {
    }

    public List<String> getEvent() {
        return event;
    }

    public void setEvent(List<String> event) {
        this.event = event;
    }

    public List<String> getHandler() {
        return handler;
    }

    public void setHandler(List<String> handler) {
        this.handler = handler;
    }

    public Map<String,String> getHandlers() {
        String eventName = null;
        String eventHandler = null;
        Map<String,String> tmpHandlersMap = new HashMap<String,String>();

        for(int i = 0; i < event.size() ; i++) {
            eventName = event.get(i);
            eventHandler = handler.get(i);
            logger.debug("Populating [{},{}]",eventName,eventHandler);
            tmpHandlersMap.put(eventName,eventHandler);
        }

        return tmpHandlersMap;
    }

}
