package core.java9.features.logging;

import java.lang.System.Logger;
import java.util.Currency;
import java.util.Set;

import static java.lang.System.Logger.Level.*;

public class LoggerRunner {

    private static Logger logger = System.getLogger("LoggerRunner");

    public static void main(String[] args) {
        // Let us load all currencies
        Set<Currency> c = Currency.getAvailableCurrencies();
        System.out.println("# of currencies: " + c.size());

        /* Let us test the platform logger by logging few messages */

        logger.log(TRACE, "Entering application.");
        logger.log(ERROR, "An unknown error occurred.");
        logger.log(INFO, "FYI");
        logger.log(TRACE, "Exiting application.");
    }
}
