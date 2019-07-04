package core.java9.features.methodhandlers.proxy.privat;

public interface PrivateInaccessible {
    default void quack() {
        System.out.println(" -> PrivateInaccessible.quack()");
    }
}
