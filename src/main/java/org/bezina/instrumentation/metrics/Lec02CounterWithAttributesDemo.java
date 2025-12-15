package org.bezina.instrumentation.metrics;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import org.bezina.instrumentation.CommonUtil;
import org.bezina.instrumentation.OpenTelemetryConfig;

import java.util.concurrent.ThreadLocalRandom;

public class Lec02CounterWithAttributesDemo {
    private static final Meter meter = OpenTelemetryConfig.meter(Lec02CounterWithAttributesDemo.class);

    public static void main() {
        var counter = createProductViewCounter();
        var productViewRecorder = new ProductViewRecorder(counter);
        var controller = new ProductController(productViewRecorder);

        for (int i = 0; i < 10_000; i++) {
            controller.viewProduct(ThreadLocalRandom.current().nextLong(1, 4));
        }
    }

    //like Spring Bean - Thread safe
    private static LongCounter createProductViewCounter() {
        return meter.counterBuilder("app.product.view.count")
                // .ofDoubles()
                .setDescription("Total number of product view")
                .setUnit("{view}")
                .build();

    }

    // REST controller
    private static class ProductController {

        private final ProductViewRecorder viewRecorder;


        private ProductController(ProductViewRecorder viewRecorder) {
            this.viewRecorder = viewRecorder;
        }

        public void viewProduct(long productId) {
            CommonUtil.sleepSeconds(1);
            this.viewRecorder.recordView(productId);
        }

    }

    //spring bean
    private static class ProductViewRecorder {
        private static final AttributeKey<Long> PRODUCT_ID_KEY = AttributeKey.longKey("productId");
        private final LongCounter counter;

        private ProductViewRecorder(LongCounter counter) {
            this.counter = counter;
        }

        public void recordView(long productId) {
            this.counter.add(1, Attributes.of(PRODUCT_ID_KEY, productId));
        }

    }

}
