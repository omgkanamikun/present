package core.java9.features.threads.compare;

import java.util.concurrent.Flow;

public class DelayedSubscriber<T> implements Flow.Subscriber<T> {

    private final String id;
    private Flow.Subscription subscription;

    public DelayedSubscriber(final String id) {
        this.id = id;
    }

    @Override
    public void onSubscribe(final Flow.Subscription subscription) {
        this.subscription = subscription;
        System.out.printf("%s subscribed!%n", this.id);
        subscription.request(1);
    }

    @Override
    public void onNext(final T item) {
        this.subscription.request(1);
        try {
            Thread.sleep(100);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s processed: %s%n", this.id, item);
    }

    @Override
    public void onError(final Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.printf("%s completed!%n", this.id);
    }

    @Override
    public String toString() {
        return String.format("Subscriber %s", this.id);
    }
}
