package org.bezina.observability.otel;

import io.opentelemetry.api.baggage.Baggage;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.context.propagation.TextMapSetter;
import org.bezina.observability.DebugRequestKeys;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class OnDemandBaggagePropagator implements TextMapPropagator {
    private static final List<String> FIELDS = List.of(DebugRequestKeys.HEADER);

    @Override
    public Collection<String> fields() {
        return FIELDS;
    }

    @Override
    public <C> void inject(Context context, C c, TextMapSetter<C> textMapSetter) {

    }

    @Override
    public <C> Context extract(Context context, C carrier, TextMapGetter<C> textMapGetter) {
     return    Optional.ofNullable(
                        textMapGetter.get(carrier, DebugRequestKeys.HEADER))
                .filter(Predicate.not(String::isEmpty))
                .map(this::toBaggage)
                .map(context::with)
                .orElse(context);

    }
//the Baggage is not the part of the context
    private Baggage toBaggage(String requestId) {
        return Baggage.current()
                    .toBuilder()
                    .put(DebugRequestKeys.BAGGAGE, requestId)
                    .build();
    }
}
