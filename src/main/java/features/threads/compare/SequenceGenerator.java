package core.java9.features.threads.compare;

import java.util.function.Supplier;

public class SequenceGenerator implements Supplier<Integer> {

    private int count = 1;

    @Override
    public Integer get() {
        return this.count++;
    }
}