package core.java9.features.methodhandlers.proxy.privat;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Proxy;

//Caused by: java.lang.IllegalAccessException: no private access for invokespecial: interface Duck, from Duck/package
public class ProxyDemoBroken {
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
