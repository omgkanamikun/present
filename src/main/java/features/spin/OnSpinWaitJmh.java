package core.java9.features.spin;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, timeUnit = TimeUnit.MICROSECONDS)
@Measurement(iterations = 5, timeUnit = TimeUnit.MICROSECONDS)
@Fork(value = 5, warmups = 1)
@State(Scope.Benchmark)
public class OnSpinWaitJmh {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(OnSpinWaitJmh.class.getName())
                .verbosity(VerboseMode.NORMAL)
                .build();
        new Runner(opt).run();
    }

    @Param({"1024"})
    public Integer iterations;

    @Benchmark()
    public long onSpinWait() {
        int i = 0, sum = 0;
        while (i++ < iterations) {
            Thread.onSpinWait();
            sum += 10;
        }
        return sum;
    }

    @Benchmark()
    public long yield() {
        int i = 0, sum = 0;
        while (i++ < iterations) {
            Thread.yield();
            sum += 10;
        }
        return sum;
    }

    @Benchmark()
    public long sleep() throws InterruptedException {
        int i = 0, sum = 0;
        while (i++ < iterations) {
            Thread.sleep(1);
            sum += 10;
        }
        return sum;
    }
}
