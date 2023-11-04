package org.javen.framework.core.env.decorator

import java.util.Properties

interface PropertiesDecorator {
    fun decorate(propertiesLine: String, properties: Properties)
}
