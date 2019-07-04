package core.java9.features.threads.compare;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DelayedSubscribers {

  public static void main(final String[] args) {
    final DelayedSubscribers delayedSubscribers = new DelayedSubscribers();
    delayedSubscribers.publishWithSubmit();
    System.out.println("===========");
    delayedSubscribers.publishWithOffer();
    System.out.println("===========");
    delayedSubscribers.publishWithOfferTimeout();
  }

  public void publishWithSubmit() {
    final SequenceGenerator sequenceGenerator = new SequenceGenerator();
    this.publish(publisher -> publisher.submit(sequenceGenerator.get()));
  }

  public void publishWithOffer() {
    final SequenceGenerator sequenceGenerator = new SequenceGenerator();
    this.publish(publisher -> publisher.offer(sequenceGenerator.get(),
        ((subscriber, value) -> {
          System.out.printf("%s dropped %s%n", subscriber, value);
          return true;
        })));
  }

  public void publishWithOfferTimeout() {
    final SequenceGenerator sequenceGenerator = new SequenceGenerator();
    this.publish(publisher ->
        publisher.offer(
            sequenceGenerator.get(),
            1000,
            TimeUnit.MILLISECONDS,
            ((subscriber, value) -> {
              System.out.printf("%s dropped %s%n", subscriber, value);
              return true;
            })
        ));
  }

  private void publish(final Consumer<PeriodicPublisher<Integer>> action) {
    final PeriodicPublisher<Integer> publisher =
        new PeriodicPublisher<>(
            action,
            16,
            50,
            50,
            TimeUnit.MILLISECONDS);
    publisher.subscribe(new DelayedSubscriber<>("1"));
    publisher.subscribe(new DelayedSubscriber<>("2"));
    publisher.subscribe(new DelayedSubscriber<>("3"));
    publisher.waitForCompletion();
    System.out.println("Publish completed");
    try {
      Thread.sleep(5000);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }

}
