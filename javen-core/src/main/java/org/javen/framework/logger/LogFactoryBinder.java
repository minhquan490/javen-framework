package org.javen.framework.logger;

public final class LogFactoryBinder {

    private LogFactoryBinder() {
    }

    private static LogFactory logFactory;

    static void bind(Class<?> logFactoryType, String getLoggerMethodName, Class<?> getLoggerMethodReturnType) {
        try {
            logFactory = new DymanicLogFactory(logFactoryType, getLoggerMethodName, getLoggerMethodReturnType);
        } catch (Exception e) {
            throw new IllegalStateException("Can not bind sl4j log factory", e);
        }
    }

    public static Log getLog(String name) {
        return logFactory.getLog(name);
    }

    public static Log getLog(Class<?> clazz) {
        return logFactory.getLog(clazz);
    }

    public static LogFactory getLogFactory() {
        return logFactory;
    }
}
