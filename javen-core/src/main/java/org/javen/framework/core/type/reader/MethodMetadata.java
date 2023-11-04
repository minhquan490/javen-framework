package org.javen.framework.core.type.reader;

import com.google.common.base.Objects;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public final class MethodMetadata implements Serializable {
    private final String methodName;
    private final String methodReturnType;
    private final boolean isVoid;
    private final String[] methodParams;

    MethodMetadata(Method method) {
        this.methodName = method.getName();
        this.methodReturnType = probeReturnType(method);
        this.isVoid = this.methodReturnType == null;
        this.methodParams = probeParameters(method);
    }

    public String getMethodName() {
        return methodName;
    }

    @Nullable
    public String getMethodReturnType() {
        return methodReturnType;
    }

    public boolean isVoid() {
        return isVoid;
    }

    @Nullable
    public String[] getMethodParams() {
        return methodParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodMetadata metadata = (MethodMetadata) o;
        return isVoid() == metadata.isVoid() && Objects.equal(getMethodName(), metadata.getMethodName()) && Objects.equal(getMethodReturnType(), metadata.getMethodReturnType()) && Objects.equal(getMethodParams(), metadata.getMethodParams());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMethodName(), getMethodReturnType(), isVoid(), getMethodParams());
    }

    private String probeReturnType(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType.equals(Void.TYPE)) {
            return null;
        } else {
            return returnType.getName();
        }
    }

    private String[] probeParameters(Method method) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 0) {
            return null;
        } else {
            return Stream.of(parameters)
                    .map(Parameter::getType)
                    .map(Class::getName)
                    .toList()
                    .toArray(new String[0]);
        }
    }
}
