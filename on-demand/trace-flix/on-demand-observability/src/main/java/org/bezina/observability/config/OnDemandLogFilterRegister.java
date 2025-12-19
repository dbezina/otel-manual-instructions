package org.bezina.observability.config;

import ch.qos.logback.classic.LoggerContext;
import org.bezina.observability.log.OnDemandLogFilter;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class OnDemandLogFilterRegister implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        //to avoid manual Turbofilters
        var isAlreadyRegistered = loggerContext.getTurboFilterList()
                .stream()
                .anyMatch(turboFilter -> turboFilter instanceof OnDemandLogFilter);
        if (isAlreadyRegistered) {
            return;
        }
        // we can register now
        var filter = new OnDemandLogFilter();
        filter.setContext(loggerContext);
        filter.start();
        loggerContext.addTurboFilter(filter);
    }
}
