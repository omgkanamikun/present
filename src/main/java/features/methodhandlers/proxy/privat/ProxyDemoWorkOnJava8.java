package core.java9.features.methodhandlers.proxy.privat;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
//--illegal-access=deny
public class ProxyDemoWorkOnJava8 {
    public static void main(String[] a) {
        Duck duck = (Duck) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { Duck.class },
                (proxy, method, args) -> {
                    Constructor<Lookup> constructor = Lookup.class
                            .getDeclaredConstructor(Class.class);
                    constructor.setAccessible(true);
                    constructor.newInstance(Duck.class)
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
