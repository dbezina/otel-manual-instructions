package org.bezina.instrumentation.trace;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import org.bezina.instrumentation.CommonUtil;
import org.bezina.instrumentation.OpenTelemetryConfig;
import org.slf4j.LoggerFactory;

public class Lec01SpanBasicsDemo {

    private static final Tracer tracer = OpenTelemetryConfig.tracer(Lec01SpanBasicsDemo.class);
    static void main(String[] args) {
        var demo = new Lec01SpanBasicsDemo();
        //POST order
        demo.processOrder();
        CommonUtil.sleepSeconds(2);
    }
    private void processOrder(){
        var span = tracer.spanBuilder("ProcessOrder").startSpan();
        try {
            processPayment();
            deductInventory();
            sendNotification();
            span.setAttribute("order.id",1204);
            span.setAttribute("order.amount",2000);

            span.setStatus(StatusCode.OK);
        }
        catch (Exception e){
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
        }
        finally {
            span.end();
        }

    }
    private void processPayment(){
        CommonUtil.sleepMillis(150);
    }
    private void deductInventory(){
        CommonUtil.sleepMillis(125);
    }

    private void sendNotification(){
        CommonUtil.sleepMillis(100);
       // throw new RuntimeException("IO Error");
    }


}
