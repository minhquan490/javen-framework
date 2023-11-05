package org.javen.framework.logger;

import org.apache.commons.lang3.StringUtils;

import java.lang.invoke.MethodHandle;

final class CommonLoggingLog implements Log {

    private final org.apache.commons.logging.Log delegate;

    CommonLoggingLog(MethodHandle invoker, Object param) throws Throwable {
        if (param instanceof String stringParam) {
            this.delegate = (org.apache.commons.logging.Log) invoker.invokeExact(stringParam);
        } else {
            this.delegate = (org.apache.commons.logging.Log) invoker.invokeExact((Class<?>) param);
        }
    }

    @Override
    public String getName() {
        return StringUtils.EMPTY;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void trace(String format, Object... arguments) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void debug(String format, Object... arguments) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void info(String format, Object... arguments) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(String format, Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(String format, Object... arguments) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void error(String msg, Throwable t) {
        this.delegate.error(msg, t);
    }
}
