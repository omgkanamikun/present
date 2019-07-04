package core.java9.features.misc;// SafeVarargsTest.java

public class SafeVarargsTest {
    // Allowed in JDK 9

    @SafeVarargs
    private <T> void print(T... args) {
        for(T element : args) {
            System.out.println(element);
        }
    }
    
    // More code goes here
}
