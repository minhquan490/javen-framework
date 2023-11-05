package org.javen.framework.logger;

public interface LogFactory {
    Log getLog(String name);

    Log getLog(Class<?> clazz);
}
