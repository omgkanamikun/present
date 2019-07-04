package core.java9.features.threads.compare;

import hu.akarnokd.rxjava2.interop.FlowInterop;
import io.reactivex.Flowable;
import reactor.adapter.JdkFlowAdapter;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

public class RxInterop {

  public static void main(final String[] args) {
    final Flow.Publisher<Long> publisher =
        Flowable.interval(0, 50, TimeUnit.MILLISECONDS)
            .take(50)
            .to(FlowInterop.toFlow());
    JdkFlowAdapter.flowPublisherToFlux(publisher)
        .map(v -> v * 10)
        .toStream()
        .forEach(System.out::println);
  }
}
