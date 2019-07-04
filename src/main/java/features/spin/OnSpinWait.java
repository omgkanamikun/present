package core.java9.features.spin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OnSpinWait {

    private static final BigDecimal FOUR = BigDecimal.valueOf(4);


    private static BigDecimal result;

    public static void main(String[] args) throws Exception {

        ProcessHandle self = ProcessHandle.current();
        System.out.println(self.pid());
        List<Callable<BigDecimal>> tasks = IntStream.range(0, 20).boxed().map((Function<Integer, Callable<BigDecimal>>) integer -> () -> computePi(50000)).collect(Collectors.toList());
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<BigDecimal>> futures = executor.invokeAll(tasks);
        executor.shutdown();
        while (executor.isTerminated()) {
            Thread.onSpinWait();
        }
        futures.stream().map(future -> {
            try {
                return future.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }).forEach(System.out::println);

    }

    public BigDecimal computeWithJoin() {
        Runnable r = () ->
        {
            result = computePi(50000);
        };
        Thread t = new Thread(r);
        t.start();
        try {
            t.join();
        } catch (InterruptedException ie) {
// Should never arrive here because interrupt() is never
// called.
        }
        return result;
    }


    /*
     * Compute the value of pi to the specified number of digits after the
     * decimal point. The value is computed using Machin's formula:
     *
     * pi/4 = 4*arctan(1/5)-arctan(1/239)
     *
     * and a power series expansion of arctan(x) to sufficient precision.
     */
    public static BigDecimal computePi(int digits) {
        int scale = digits + 5;
        BigDecimal arctan1_5 = arctan(5, scale);
        BigDecimal arctan1_239 = arctan(239, scale);
        BigDecimal pi = arctan1_5.multiply(FOUR).
                subtract(arctan1_239).multiply(FOUR);
        return pi.setScale(digits, RoundingMode.HALF_UP);
    }

    /*
     * Compute the value, in radians, of the arctangent of the inverse of
     * the supplied integer to the specified number of digits after the
     * decimal point. The value is computed using the power series
     * expansion for the arc tangent:
     *
     * arctan(x) = x-(x^3)/3+(x^5)/5-(x^7)/7+(x^9)/9 ...
     */
    public static BigDecimal arctan(int inverseX, int scale) {
        BigDecimal result, numer, term;
        BigDecimal invX = BigDecimal.valueOf(inverseX);
        BigDecimal invX2 = BigDecimal.valueOf(inverseX * inverseX);
        numer = BigDecimal.ONE.divide(invX, scale, RoundingMode.HALF_UP);
        result = numer;
        int i = 1;
        do {
            numer = numer.divide(invX2, scale, RoundingMode.HALF_UP);
            int denom = 2 * i + 1;
            term = numer.divide(BigDecimal.valueOf(denom), scale, RoundingMode.HALF_UP);
            if ((i % 2) != 0) {
                result = result.subtract(term);
            } else {
                result = result.add(term);
            }
            i++;
        }
        while (term.compareTo(BigDecimal.ZERO) != 0);
        return result;
    }
}
