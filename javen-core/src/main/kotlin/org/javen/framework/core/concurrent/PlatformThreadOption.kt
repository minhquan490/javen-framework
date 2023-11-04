package org.javen.framework.core.concurrent

import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

internal class PlatformThreadOption : AbstractThreadOption() {
    private val platformThreadFactory = configPlatformThreadFactory()
    override fun getThreadFactory(): ThreadFactory {
        return platformThreadFactory
    }

    private fun configPlatformThreadFactory(): ThreadFactory {
//        return Thread.ofPlatform()
//                .name("platform-thread")
//                .inheritInheritableThreadLocals(false)
//                .factory();
        return Executors.defaultThreadFactory()
    }
}
