package org.javen.framework.utils

import org.javen.framework.core.annotation.Reachable
import sun.misc.Unsafe
import java.lang.reflect.Field

@Reachable(onClasses = [UnsafeUtils::class], fieldNames = ["theUnsafe"])
class UnsafeUtils private constructor() {
    companion object {
        private var INSTANCE: Unsafe? = null

        init {
            try {
                val theUnsafeField: Field = Unsafe::class.java.getDeclaredField("theUnsafe")
                theUnsafeField.setAccessible(true)
                INSTANCE = theUnsafeField.get(null) as Unsafe?
            } catch (e: NoSuchFieldException) {
                throw IllegalStateException("Can not obtain the unsafe", e)
            } catch (e: IllegalAccessException) {
                throw IllegalStateException("Can not obtain the unsafe", e)
            }
        }

        fun getUnsafe(): Unsafe {
            return INSTANCE!!
        }

        @Throws(InstantiationException::class)
        fun <T> allocateInstance(type: Class<T>): T {
            checkUnsafe()
            return type.cast(getUnsafe().allocateInstance(type))
        }

        fun getPointerAddress(address: Long): Int {
            checkUnsafe()
            return getUnsafe().getInt(address)
        }

        private fun checkUnsafe() {
            checkNotNull (INSTANCE) {
                "The Unsafe already null"
            }
        }
    }
}