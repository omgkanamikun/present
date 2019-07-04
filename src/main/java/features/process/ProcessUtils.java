package core.java9.features.process;

import java.io.File;
import java.io.IOException;

public class ProcessUtils {

    public static String getClassPath() {
        String cp = System.getProperty("java.class.path");
        System.out.println("ClassPath is " + cp);
        return cp;
    }

    public static File getJavaCmd() throws IOException {
        String javaHome = System.getProperty("java.home");
        File javaCmd;
        if (System.getProperty("os.name").startsWith("Win")) {
            javaCmd = new File(javaHome, "bin/java.exe");
        } else {
            javaCmd = new File(javaHome, "bin/java");
        }
        if (javaCmd.canExecute()) {
            return javaCmd;
        } else {
            throw new UnsupportedOperationException(javaCmd.getCanonicalPath() + " is not executable");
        }
    }

}
