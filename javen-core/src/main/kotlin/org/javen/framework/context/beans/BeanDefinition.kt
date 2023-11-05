package org.javen.framework.context.beans

import org.javen.framework.core.Component
import org.javen.framework.core.Ordered
import org.javen.framework.core.Scope
import org.javen.framework.core.type.reader.ClassMetadata
import org.javen.framework.core.type.reader.ConstructorMetadata
import java.io.Serializable

class BeanDefinition(
    private val classMetadata: ClassMetadata,
    private val beanName: String,
    component: Component,
    scope: Scope?
) :
    Ordered, Serializable {

    private val _isLazy: Boolean
    private val _isSingleton: Boolean
    private val _javaType: Class<*>
    private var _order: Int

    init {
        this._isLazy = component.lazy
        this._isSingleton = isBeanSingleton(scope)
        this._javaType = Class.forName("${classMetadata.packageName}.${classMetadata.simpleClassName}")
        this._order = component.order
    }

    fun isLazy(): Boolean {
        return this._isLazy
    }

    fun isSingleton(): Boolean {
        return this._isSingleton
    }

    fun getBeanName(): String {
        return this.beanName
    }

    fun getType(): Class<*> {
        return this._javaType
    }

    fun setOrder(order: Int) {
        this._order = order
    }

    fun getConstructorPrimary(): ConstructorMetadata? {
        val constructors: Array<out ConstructorMetadata> = classMetadata.constructors ?: return null

        for (con in constructors) {
            if (con.isPrimary) {
                return con
            }
        }

        return null
    }

    fun getPrimaryConstructorParameters(): Array<Class<*>> {

        val constructor: ConstructorMetadata? = getConstructorPrimary()

        return if (constructor != null) {
            loadType(constructor.paramTypes)
        } else {
            emptyArray()
        }
    }

    private fun isBeanSingleton(scope: Scope?): Boolean {
        return scope == null || scope.value == Scope.ControllerScope.SINGLETON
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

    override fun getOrder(): Int {
        return this._order
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeanDefinition

        if (classMetadata != other.classMetadata) return false
        if (_isLazy != other._isLazy) return false
        if (_isSingleton != other._isSingleton) return false

        return true
    }

    override fun hashCode(): Int {
        var result = classMetadata.hashCode()
        result = 31 * result + _isLazy.hashCode()
        result = 31 * result + _isSingleton.hashCode()
        return result
    }

}