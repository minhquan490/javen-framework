package org.javen.framework.logger;

import org.slf4j.Logger;

import java.lang.invoke.MethodHandle;

class Sl4jLog implements Log {

    private final Logger delegate;

    Sl4jLog(MethodHandle invoker, Object param) throws Throwable {
        if (param instanceof String stringParam) {
            this.delegate = (Logger) invoker.invokeExact(stringParam);
        } else {
            this.delegate = (Logger) invoker.invokeExact((Class<?>) param);
        }
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return this.delegate.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        this.delegate.trace(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        this.delegate.trace(format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        this.delegate.trace(format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        this.delegate.trace(format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        this.delegate.trace(msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.delegate.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        this.delegate.debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        this.delegate.debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        this.delegate.debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        this.delegate.debug(format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        this.delegate.debug(msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return this.delegate.isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        this.delegate.info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        this.delegate.info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        this.delegate.info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        this.delegate.info(format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        this.delegate.info(msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return this.delegate.isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        this.delegate.warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        this.delegate.warn(format, arg);
    }

    @Override
    public void warn(String format, Object... arguments) {
        this.delegate.warn(format, arguments);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        this.delegate.warn(format, arg1, arg2);
    }

    @Override
    public void warn(String msg, Throwable t) {
        this.delegate.warn(msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return this.delegate.isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        this.delegate.error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        this.delegate.error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        this.delegate.error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        this.delegate.error(format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        this.delegate.error(msg, t);
    }
}
