// Test.java
package core.java9.features.modules;

import java.lang.reflect.Method;

public class Test {

    public static void main(String[] args) throws Exception {

        ModuleLayer layer = null;/* create a layer... */

// Load a class using the layer
        String moduleName = "some.module.layer";
        String className = "some.module.layer.LayerInfo";
        Class<?> cls = layer.findLoader(moduleName)
                .loadClass(className);

// A method name that prints the details of an object
        String methodName = "printInfo";

// Instantiate the class using its no-args constructor
        Object obj = cls.getConstructor().newInstance();

// Find the method
        Method method = cls.getMethod(methodName);

// Call the method that will print the details
        method.invoke(obj);

    }
}
