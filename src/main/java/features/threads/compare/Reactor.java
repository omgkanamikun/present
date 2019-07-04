package core.java9.features.threads.compare;

import reactor.adapter.JdkFlowAdapter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Reactor {

  public void toFlow() {
    JdkFlowAdapter.publisherToFlowPublisher(
        Flux.interval(Duration.ZERO, Duration.ofMillis(50))
            .take(50)
    ).subscribe(new DelayedSubscriber<>("1"));
  }

  public void fromFlow() {
    final SequenceGenerator sequenceGenerator = new SequenceGenerator();

    final PeriodicPublisher<Integer> publisher =
        new PeriodicPublisher<>(
            pub -> pub.submit(sequenceGenerator.get()),
            16,
            50,
            50,
            TimeUnit.MILLISECONDS);
    JdkFlowAdapter.flowPublisherToFlux(publisher)
        .map(v -> v * 10)
        .subscribe(System.out::println);
  }

  public static void main(final String[] args) {
    final Reactor reactor = new Reactor();
    reactor.toFlow();
    reactor.fromFlow();
    try {
      Thread.sleep(10000);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }
}
