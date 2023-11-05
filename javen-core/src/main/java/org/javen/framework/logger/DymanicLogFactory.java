package org.javen.framework.logger;

import org.javen.framework.utils.ClassUtils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;

class DymanicLogFactory implements LogFactory {

    private static final MutableCallSite getLoggerStringCallSite;
    private static final MethodHandle getLoggerStringInvoker;

    private static final MutableCallSite getLoggerClassCallSite;
    private static final MethodHandle getLoggerClassInvoker;

    private static final Class<?> logType = getLogType();

    static {
        MethodType getLoggerStringType = MethodType.methodType(logType, String.class);
        getLoggerStringCallSite = new MutableCallSite(getLoggerStringType);
        getLoggerStringInvoker = getLoggerStringCallSite.dynamicInvoker();

        MethodType getLoggerClassType = MethodType.methodType(logType, Class.class);
        getLoggerClassCallSite = new MutableCallSite(getLoggerClassType);
        getLoggerClassInvoker = getLoggerClassCallSite.dynamicInvoker();
    }

    DymanicLogFactory(Class<?> logFactoryType, String getLoggerMethodName, Class<?> getLoggerMethodReturnType) throws NoSuchMethodException, IllegalAccessException {
        MethodHandle getLoggerStringMethod = MethodHandles.lookup().findStatic(logFactoryType, getLoggerMethodName, MethodType.methodType(getLoggerMethodReturnType, String.class));
        getLoggerStringCallSite.setTarget(getLoggerStringMethod);

        MethodHandle getLoggerClassMethod = MethodHandles.lookup().findStatic(logFactoryType, getLoggerMethodName, MethodType.methodType(getLoggerMethodReturnType, Class.class));
        getLoggerClassCallSite.setTarget(getLoggerClassMethod);

    }

    @Override
    public Log getLog(String name) {
        try {
            return new DynamicLog(logType.getName(), getLoggerStringInvoker, name);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Log getLog(Class<?> clazz) {
        try {
            return new DynamicLog(logType.getName(), getLoggerClassInvoker, clazz);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    private static Class<?> getLogType() {
        if (ClassUtils.isPresent("org.slf4j.Logger", null)) {
            return loadClass("org.slf4j.Logger");
        }
        if (ClassUtils.isPresent("org.apache.logging.log4j.Logger", null)) {
            return loadClass("org.apache.logging.log4j.Logger");
        }
        if (ClassUtils.isPresent("org.apache.commons.logging.Log", null)) {
            return loadClass("org.apache.commons.logging.Log");
        }
        throw new IllegalStateException("Can not load logging framework");
    }

    private static Class<?> loadClass(String className) {
        try {
            return ClassUtils.forName(className, null);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
