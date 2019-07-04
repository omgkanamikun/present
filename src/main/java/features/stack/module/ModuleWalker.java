package core.java9.features.stack.module;


import static java.lang.StackWalker.Option.RETAIN_CLASS_REFERENCE;

public class ModuleWalker {
    public static void main(String[] args) {
        System.out.println(isModulePresent("slf4j.api"));
    }

    //require static example
    public static boolean isModulePresent(String moduleName) {
        return StackWalker
                .getInstance(RETAIN_CLASS_REFERENCE)
                .walk(frames -> frames
                        .map(StackWalker.StackFrame::getDeclaringClass)
                        .filter(declaringClass ->
                                declaringClass != ModuleWalker.class)
                        .findFirst()
                        .orElse((Class) ModuleWalker.class))
                .getModule()
                .getLayer()
                .findModule(moduleName)
                .isPresent();
        // chain all the methods!
    }
}
