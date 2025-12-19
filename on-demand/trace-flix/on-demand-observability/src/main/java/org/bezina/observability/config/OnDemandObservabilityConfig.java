package org.bezina.observability.config;

import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import org.bezina.observability.otel.OnDemandBaggagePropagator;
import org.bezina.observability.otel.OnDemandTraceSampler;
import org.bezina.observability.web.BaggageMdcBridgeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnDemandObservabilityConfig {

    @Bean
    public BaggageMdcBridgeFilter baggageMdcBridgeFilter() {
        return new BaggageMdcBridgeFilter();
    }

    @Bean
    public OnDemandLogFilterRegister onDemandLogFilterRegister() {
        return new OnDemandLogFilterRegister();
    }

    @Bean
    public AutoConfigurationCustomizerProvider autoConfigurationCustomizerProvider() {
        return autoConfiguration -> autoConfiguration
                .addSamplerCustomizer(((sampler, configProperties) ->
                        new OnDemandTraceSampler(sampler)))
                .addPropagatorCustomizer(this::customizePropagator);
    }

    // W3CTraceContextPropagator
    // W3CBaggagePropagator
    //helper method
    private TextMapPropagator customizePropagator(TextMapPropagator propagator, ConfigProperties configProperties) {
        return switch (propagator) {
            case W3CBaggagePropagator w3CBaggagePropagator -> TextMapPropagator
                    .composite(new OnDemandBaggagePropagator(), w3CBaggagePropagator);
            case null, default -> propagator;
        };
    }
}
