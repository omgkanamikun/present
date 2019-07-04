package core.java9.features.threads.async;

import java.util.ArrayList;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class BatchSubscriberImpl<T> implements Subscriber<String> {
    private Subscription subscription;
    private boolean completed = false;
    private int counter;
    private ArrayList<String> buffer;
    public static final int BUFFER_SIZE = 5;

    public BatchSubscriberImpl() {
        buffer = new ArrayList<String>();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(BUFFER_SIZE);
    }

    @Override
    public void onNext(String item) {
        buffer.add(item);
        // if buffer is full, process the items.
        if (buffer.size() >= BUFFER_SIZE) {
            processBuffer();
        }
        //request more items.
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        completed = true;
        // process any remaining items in buffer before
        processBuffer();
        subscription.cancel();
    }

    private void processBuffer() {
        if (buffer.isEmpty()) {
            return;
        }
        // Process all items in the buffer. Here, we just print it and sleep for 1 second.
        System.out.print("Processed items: ");
        buffer
                .forEach(item -> {
                    System.out.print(" " + item);
                });
        System.out.println();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counter = counter + buffer.size();
        buffer.clear();
    }
}
