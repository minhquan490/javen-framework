package org.javen.framework.context.event;

import org.javen.framework.context.InstanceObject;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface EventListener<T> extends InstanceObject {
    Class<? extends ApplicationEvent> listeningOn();

    void handle(T eventData) throws Exception;

    @Nullable
    default Class<T> getDataType() {
        Type[] genericTypes = this.getClass().getGenericInterfaces();
        for (Type type : genericTypes) {
            if (type.getTypeName().equals(EventListener.class.getName())) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                return (Class<T>) parameterizedType.getActualTypeArguments()[0];
            }
        }
        return null;
    }
}
