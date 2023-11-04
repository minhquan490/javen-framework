package org.javen.framework.core.type.reader;

import com.google.common.base.Objects;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.stream.Stream;
import org.javen.framework.core.annotation.Primary;

public final class ConstructorMetadata implements Serializable {
    private final String[] paramTypes;
    private final boolean isPrimary;

    public ConstructorMetadata(Constructor<?> constructor) {
        this.paramTypes = Stream.of(constructor.getParameterTypes())
                .map(Class::getName)
                .toList()
                .toArray(new String[0]);
        this.isPrimary = constructor.isAnnotationPresent(Primary.class);
    }

    public String[] getParamTypes() {
        return paramTypes;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstructorMetadata that = (ConstructorMetadata) o;
        return isPrimary() == that.isPrimary() && Objects.equal(getParamTypes(), that.getParamTypes());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getParamTypes(), isPrimary());
    }
}
