package org.bezina.instrumentation.trace;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import org.bezina.instrumentation.CommonUtil;
import org.bezina.instrumentation.OpenTelemetryConfig;

public class Lec03ExplicitParentChildDemo {
    private static final Tracer tracer = OpenTelemetryConfig.tracer(Lec02ParentChildDemo.class);

    public static void main(String[] args) {

        var demo = new Lec03ExplicitParentChildDemo();
        //POST /orders
        demo.processOrder();

        CommonUtil.sleepSeconds(2);

    }

    private void processOrder() {
        var span = tracer.spanBuilder("processOrder")
                .setNoParent()
                .startSpan();
        try{
            processPayment(span);
            Thread.ofPlatform().start(() -> deductInventory(span));
            Thread.ofVirtual().start(() -> deductInventory(span));
//            Thread.ofPlatform().start(() -> deductInventory(span));
//            deductInventory(span);
//            sendNotification(span);

            span.setAttribute("order.id", 124);
            span.setAttribute("order.amount", 1500);

            span.setStatus(StatusCode.OK);
        } catch (Exception e) {
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
        }finally {
            span.end();
        }
    }

    private void processPayment(Span parentSpan) {
        var span = tracer.spanBuilder("processPayment")
                            .setParent(Context.current().with(parentSpan))
                            .startSpan();
        try{
            CommonUtil.sleepMillis(150);
            span.setAttribute("payment.method", "CREDIT_CARD");
            span.setStatus(StatusCode.OK);
        } catch (Exception e) {
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
        }finally {
            span.end();
        }
    }

    private void deductInventory(Span parentSpan) {
        var span = tracer.spanBuilder("deductInventory")
                         .setParent(Context.current().with(parentSpan))
                         .startSpan();
        try {
          //  sendNotification();
            CommonUtil.sleepMillis(125);
            span.setStatus(StatusCode.OK);
        } catch (Exception e) {
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
        }finally {
            span.end();
        }
    }

    private void sendNotification(Span parentSpan) {
        var span = tracer.spanBuilder("sendNotification")
                .setParent(Context.current().with(parentSpan))
                .startSpan();
        try{
            CommonUtil.sleepMillis(100);
            span.setStatus(StatusCode.OK);
        } catch (Exception e) {
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
        }finally {
            span.end();
        }
    }

}
