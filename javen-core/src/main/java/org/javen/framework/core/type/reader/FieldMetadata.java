package org.javen.framework.core.type.reader;

import com.google.common.base.Objects;
import java.io.Serializable;
import java.lang.reflect.Field;

public final class FieldMetadata implements Serializable {
    private final String fieldName;
    private final String fieldType;

    FieldMetadata(Field field) {
        this.fieldName = field.getName();
        this.fieldType = field.getType().getName();
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldMetadata that = (FieldMetadata) o;
        return Objects.equal(getFieldName(), that.getFieldName()) && Objects.equal(getFieldType(), that.getFieldType());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFieldName(), getFieldType());
    }
}
