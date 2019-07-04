package core.java9.features.methodhandlers.proxy;

import java.lang.reflect.Proxy;

public class ProxyDemo {
    interface Duck {
        void quack();
    }

    public static void main(String[] a) {
        Duck duck = (Duck) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { Duck.class },
                (proxy, method, args) -> {
                    System.out.println("Quack");
                    return null;
                }
        );

        duck.quack();
    }
}
