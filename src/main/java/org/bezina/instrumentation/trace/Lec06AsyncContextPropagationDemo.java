package org.bezina.instrumentation.trace;

import org.bezina.instrumentation.CommonUtil;

import io.opentelemetry.context.Context;


public class Lec06AsyncContextPropagationDemo {

    public static void main(String[] args) {

        var demo = new Lec06AsyncContextPropagationDemo();
        //POST /orders
        demo.processOrder();

        CommonUtil.sleepSeconds(2);

    }

    private void processOrder() {
        TraceUtil.trace("processOrder", span -> {
            //      Context.current().wrap(this::processOrder); consumes and returns runnable
            var t1 = Thread.ofVirtual().start(Context.current().wrap(this::processPayment));
            var t2 = Thread.ofVirtual().start(Context.current().wrap(this::deductInventory));
            var t3 = Thread.ofVirtual().start(Context.current().wrap(this::sendNotification));
//            processPayment();
//            deductInventory();
//            sendNotification();
            this.awaitCompletion(t1, t2, t3);
            span.setAttribute("order.id", 124);
            span.setAttribute("order.amount", 1500);
        });
    }

    private void processPayment() {
        TraceUtil.trace("processPayment", span -> {
            CommonUtil.sleepMillis(150);
            span.addEvent("Payment failed.... retry");
            CommonUtil.sleepMillis(150);
            span.setAttribute("payment.method", "CREDIT_CARD");
        });
    }

    private void deductInventory() {
        TraceUtil.trace("deductInventory", span -> {
            CommonUtil.sleepMillis(125);
        });
    }

    private void sendNotification() {
        TraceUtil.trace("sendNotification", span -> {
            CommonUtil.sleepMillis(100);
        });
    }

    private void awaitCompletion(Thread... threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
