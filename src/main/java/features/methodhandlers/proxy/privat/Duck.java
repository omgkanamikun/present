package core.java9.features.methodhandlers.proxy.privat;

interface Duck {
    default void quack() {
        System.out.println("Quack");
    }
}
