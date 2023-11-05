package org.javen.framework.logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LogFactoryBinderTest {

    @Test
    void bind_DoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> LogFactoryBinder.bind(LoggerFactory.class, "getLogger", Logger.class));
    }

    @Test
    void bind_ThrowIllegalStateException() {
        Assertions.assertThrows(IllegalStateException.class, () -> LogFactoryBinder.bind(LoggerFactory.class, "get", Logger.class));
    }

    @Test
    void getLogWithClass_DoesNotThrow() {
        LogFactoryBinder.bind(LoggerFactory.class, "getLogger", Logger.class);
        Assertions.assertDoesNotThrow(() -> LogFactoryBinder.getLog(getClass()));
    }

    @Test
    void getLogWithString_DoesNotThrow() {
        LogFactoryBinder.bind(LoggerFactory.class, "getLogger", Logger.class);
        Assertions.assertDoesNotThrow(() -> LogFactoryBinder.getLog(getClass().getName()));
    }

    @Test
    void getLog_NormalCase() {
        LogFactoryBinder.bind(LoggerFactory.class, "getLogger", Logger.class);
        Log log = LogFactoryBinder.getLog(getClass());
        Assertions.assertDoesNotThrow(() -> log.info("Test"));
    }
}
