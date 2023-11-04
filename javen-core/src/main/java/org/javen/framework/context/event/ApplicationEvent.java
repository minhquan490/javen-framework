package org.javen.framework.context.event;

import org.javen.framework.context.InstanceObject;

public interface ApplicationEvent extends InstanceObject {

    Object getData();

    Class<?> getDataType();
}
