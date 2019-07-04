package core.java9.features.methodhandlers.proxy;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Proxy;

public class ProxyDemoMethodHandlers {
    interface Duck {
        default void quack() {
            System.out.println("Quack");
        }
    }

    public static void main(String[] a) {
        Duck duck = (Duck) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { Duck.class },
                (proxy, method, args) -> {
                    MethodHandles
                            .lookup()
                            .in(Duck.class)
                            .unreflectSpecial(method, Duck.class)
                            .bindTo(proxy)
                            .invokeWithArguments();
                    return null;
                }
        );

        duck.quack();
    }
}
