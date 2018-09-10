package com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.infrastructure.domain.event;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.exception.TicketApplicationException;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.configuration.EventHandlersConfig;
import com.redhat.jbuenosv.ocpmicroservicesddd.sales.ticketing.application.handler.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by jlbuenosvinos.
 */
@Component
public class DomainEventPublisher implements ApplicationContextAware {

    public static final Logger logger = LoggerFactory.getLogger(DomainEventPublisher.class);

    @Autowired
    private EventHandlersConfig handlersConfig;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        logger.debug("ApplicationContext loaded.");
    }

    /**
     * Handles an event
     * @param event event to be handled
     * @param <T> event type
     */
    public <T> void publish(final T event) {
        Class eventClass = event.getClass();
        Map<String,String> handlers = handlersConfig.getHandlers();
        String handlerClass = handlers.get(eventClass.getName());
        AutowireCapableBeanFactory factory = null;
        Object clsObj = null;

        try {
            factory = applicationContext.getAutowireCapableBeanFactory();
            Class cls = Class.forName(handlerClass);
            clsObj = factory.autowire(cls,AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,Boolean.TRUE);
            EventHandler eventHandler = (EventHandler)clsObj;
            eventHandler.handle(event);
        }
        catch(Exception e) {
            throw new TicketApplicationException(e);
        }
    }

    /**
     * Handles an event list
     * @param events event list to be handled
     * @param <T> event type
     */
    public <T> void publish(final List<T> events) {
        Class cls = null;
        Class eventClass = null;
        String handlerClass = null;
        Map<String,String> handlers = null;
        EventHandler eventHandler = null;
        AutowireCapableBeanFactory factory = null;
        Object clsObj = null;

        int numEvents = events.size();

        if (numEvents > 0) {
            handlers = handlersConfig.getHandlers();
            try {
                factory = applicationContext.getAutowireCapableBeanFactory();
                for (int i = 0; i < numEvents; i++) {
                    eventClass = events.get(i).getClass();
                    handlerClass = handlers.get(eventClass.getName());
                    cls = Class.forName(handlerClass);
                    clsObj = factory.autowire(cls,AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,Boolean.TRUE);
                    eventHandler = (EventHandler) clsObj;
                    eventHandler.handle(events.get(i));
                }
            } catch (Exception e) {
                throw new TicketApplicationException(e);
            }
        }
    }

}
