package net.bearmine.nso_core.lib.data_base;

public class logger {
    public static void log(String log){
        System.out.println(log);
    }
    public static void error(Exception e){
        log("Error: "+e.getClass().getSimpleName());
        log("Message: "+e.getMessage());        log("at: "+e.getCause().getLocalizedMessage());
    }
}
