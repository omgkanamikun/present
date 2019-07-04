// QueryModule.java
package core.java9.features.modules;

import java.sql.Driver;

public class QueryModule {
    public static void main(String[] args) throws Exception {
        Class<QueryModule> cls = QueryModule.class;
        Module m = cls.getModule();

        // Check if this module can read the java.sql module
        Module javaSqlModule = Driver.class.getModule();
        boolean canReadJavaSql = m.canRead(javaSqlModule);

        // Check if this module exports the com.jdojo.module.api package to all modules
        boolean exportsModuleApiPkg =  m.isExported("reactor.core");

        // Check if this module exports the com.jdojo.module.api package to java.sql module
        boolean exportsModuleApiPkgToJavaSql = 
                m.isExported("reactor.core", javaSqlModule);

        // Check if this module opens the com.jdojo.module.api package to java.sql module
        boolean openModuleApiPkgToJavaSql = m.isOpen("reactor.core", javaSqlModule);
                
        // Print module type and name
        System.out.printf("Named Module: %b%n", m.isNamed());
        System.out.printf("Module Name: %s%n", m.getName());
        System.out.printf("Can read java.sql? %b%n", canReadJavaSql);
        System.out.printf("Exports reactor.core? %b%n", exportsModuleApiPkg);
        System.out.printf("Exports reactor.core to java.sql? %b%n",
                exportsModuleApiPkgToJavaSql);
        System.out.printf("Opens reactor.core to java.sql? %b%n",
                openModuleApiPkgToJavaSql);
    }
}
