package org.javen.framework.context.beans

import org.javen.framework.core.annotation.Component
import org.javen.framework.core.type.reader.ClassMetadata
import org.javen.framework.core.type.reader.ConstructorMetadata
import java.io.Serializable

class BeanDefinition(private val classMetadata: ClassMetadata, private val beanName: String, component: Component) :
    Serializable {

    private val isLazy: Boolean
    private val isSingleton: Boolean
    private val javaType: Class<*>

    init {
        this.isLazy = component.lazy
        this.isSingleton = component.scope == Component.Scope.SINGLETON
        this.javaType = Class.forName("${classMetadata.packageName}.${classMetadata.simpleClassName}")
    }

    fun isLazy(): Boolean {
        return this.isLazy
    }

    fun isSingleton(): Boolean {
        return this.isSingleton
    }

    fun getBeanName(): String {
        return this.beanName
    }

    fun getType(): Class<*> {
        return this.javaType
    }

    fun getPrimaryConstructorParameters(): Array<Class<*>> {
        val constructors: Array<ConstructorMetadata>? = classMetadata.constructors
        if (constructors != null) {
            for (con in constructors) {
                if (con.isPrimary) {
                    return loadType(con.paramTypes)
                }
            }
        }
        val constructor: ConstructorMetadata = constructors?.get(0) ?: return emptyArray()
        return loadType(constructor.paramTypes)
    }

    private fun loadType(names: Array<String>?): Array<Class<*>> {
        return if (names == null) {
            emptyArray()
        } else {
            val results: MutableList<Class<*>> = ArrayList(names.size)
            for (name in names) {
                val loaded: Class<*> = Class.forName(name)
                results.add(loaded)
            }
            results.toTypedArray()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeanDefinition

        if (classMetadata != other.classMetadata) return false
        if (isLazy != other.isLazy) return false
        if (isSingleton != other.isSingleton) return false

        return true
    }

    override fun hashCode(): Int {
        var result = classMetadata.hashCode()
        result = 31 * result + isLazy.hashCode()
        result = 31 * result + isSingleton.hashCode()
        return result
    }

}