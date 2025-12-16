package org.bezina.instrumentation.logs;

import io.opentelemetry.api.OpenTelemetry;
import org.bezina.instrumentation.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public void processPayment(int amount) {
        logger.debug("[DEBUG] processPayment, amount={}", amount);
        logger.info("Payment started");
        CommonUtil.sleepMillis(50);
        logger.info("Payment in process amount: " + amount);
    }
}
