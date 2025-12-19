package org.bezina.observability.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.bezina.observability.DebugRequestKeys;
import org.slf4j.MDC;
import org.slf4j.Marker;

import java.util.Objects;

public class OnDemandLogFilter extends TurboFilter {
    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String s, Object[] objects, Throwable throwable) {
        var requestId = MDC.get(DebugRequestKeys.MDC);
        return Objects.nonNull(requestId) ? FilterReply.ACCEPT : FilterReply.NEUTRAL;
    }
}
