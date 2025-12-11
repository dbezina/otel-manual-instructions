package org.bezina.instrumentation.metrics;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import org.bezina.instrumentation.CommonUtil;
import org.bezina.instrumentation.OpenTelemetryConfig;

public class Lec01CounterDemo {
    private static final Meter meter = OpenTelemetryConfig.meter(Lec01CounterDemo.class);

    public static void main() {
        var counter = createProductViewCounter();
        var controller = new ProductController(counter);

        for (int i = 0; i < 10_000; i++) {
            controller.viewProduct();
        }
    }
    //like Spring Bean - Thread safe
    private static LongCounter createProductViewCounter(){
        return meter.counterBuilder("app.product.view.count")
               // .ofDoubles()
                .setDescription("Total number of product view")
                .setUnit("{view}")
                .build();

    }
    private static class ProductController {

        private final LongCounter counter;

        private ProductController(LongCounter counter) {
            this.counter = counter;
        }

        public void viewProduct(){
            CommonUtil.sleepSeconds(1);
            this.counter.add(1);
        }

    }

}
