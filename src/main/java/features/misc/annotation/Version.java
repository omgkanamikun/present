package core.java9.features.misc.annotation;// Version.java

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({PACKAGE, MODULE, TYPE})
public @interface Version {
    int major();
    int minor();
}
