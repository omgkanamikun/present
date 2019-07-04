package core.java9.features.methodhandlers.proxy.privat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Proxy;

public class ProxyDemoFindSpecial {
    public static void main(String[] a) {
        Duck duck = (Duck) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{Duck.class},
                (proxy, method, args) -> {
                    MethodHandles.lookup()
                            .findSpecial(
                                    Duck.class,
                                    "quack",
                                    MethodType.methodType(
                                            void.class,
                                            new Class[0]),
                                    Duck.class)
                            .bindTo(proxy)
                            .invokeWithArguments();
                    return null;
                }
        );

        duck.quack();
    }
}
