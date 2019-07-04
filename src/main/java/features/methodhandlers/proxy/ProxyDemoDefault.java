package core.java9.features.methodhandlers.proxy;

import java.lang.reflect.Proxy;

public class ProxyDemoDefault {
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
                    method.invoke(proxy);
                    return null;
                }
        );

        duck.quack();
    }
}
