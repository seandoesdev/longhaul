package seandoesdev.story01.serviceLocatorPattern;

import java.util.*;

import javax.naming.InitialContext;

public class ServiceLocator {

    private InitialContext ic;
    private Map cache;
    private static ServiceLocator me;
    static {
        me = new ServiceLocator();
    }
    private ServiceLocator() {
        cache = Collections.synchronizedMap(new HashMap());
    }

    public InitialContext getiInitialContext() throws Exception{
        try {
            if (ic == null) {
                ic = new InitialContext();
            }
        } catch (Exception e) {
            throw new Exception("InitialContext error", e);
        }
        return ic;
    }

    public static ServiceLocator getInstance() {
        if (me == null) {
            me = new ServiceLocator();
        }
        return me;
    }

}