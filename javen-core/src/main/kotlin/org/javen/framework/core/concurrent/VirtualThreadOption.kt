package org.javen.framework.core.concurrent

import java.util.concurrent.ThreadFactory

internal class VirtualThreadOption : AbstractThreadOption() {
    private val virtualThreadFactory = configVirtualThreadFactory()
    override fun getThreadFactory(): ThreadFactory {
        return virtualThreadFactory
    }

    private fun configVirtualThreadFactory(): ThreadFactory {
//        return Thread.ofVirtual()
//                .name("virtual-thread")
//                .inheritInheritableThreadLocals(false)
//                .factory();
        throw UnsupportedOperationException("Support when kotlin allow compile to JVM21")
    }
}