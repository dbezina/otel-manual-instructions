package org.bezina.instrumentation.logs;

import ch.qos.logback.classic.PatternLayout;
import org.bezina.instrumentation.CommonUtil;
import org.bezina.instrumentation.OpenTelemetryConfig;
import org.slf4j.MDC;

public class Lec02TurboFilterDemo {
    private static final PaymentService paymentService = new PaymentService();

    public static void main(String[] args) throws Exception {
        //paymentService.processPayment(100);
        OpenTelemetryConfig.setupLoggingAppender();
        for (int i = 0; i< 10; i++){
            processRequest(i+1);
        }

        CommonUtil.sleepSeconds(1);

    }

    private static void processRequest(int userId) {
        try (var ignore = MDC.putCloseable("userId", String.valueOf(userId))) {
            paymentService.processPayment(userId * 10);
        }
    }
}
