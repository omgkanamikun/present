// ProcessorTest.java
package core.java9.features.threads.subscriber;

import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

public class ProcessorTest {

    public static void main(String[] args) {

        // The publisher is closed when the try block exits
        try (SubmissionPublisher<Long> pub = new SubmissionPublisher<>()) {
            // Create a Subscriber
            SimpleSubscriber sub = new SimpleSubscriber("S1", 10);

            // Create a processor
            FilterProcessor<Long> filter = new FilterProcessor<>(n -> n % 2 == 0);

            // Subscribe the filter to the publisher and a subscriber to the filter
            pub.subscribe(filter);            
            filter.subscribe(sub);

            // Generate and publish 6 integers
            LongStream.range(1L, 7L)
                      .forEach(pub::submit);
        }

        try {
            // Sleep for two seconds to let subscribers finish handling all items
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

