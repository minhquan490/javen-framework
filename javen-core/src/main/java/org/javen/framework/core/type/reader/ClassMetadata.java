package org.javen.framework.core.type.reader;

import com.google.common.base.Objects;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class ClassMetadata implements Serializable {
    private final String packageName;
    private final String simpleClassName;
    private final FieldMetadata[] fields;
    private final MethodMetadata[] methods;
    private final ConstructorMetadata[] constructors;

    ClassMetadata(Class<?> javaType) {
        this.packageName = javaType.getPackageName();
        this.simpleClassName = javaType.getSimpleName();
        this.fields = probeFields(javaType);
        this.methods = probeMethods(javaType);
        this.constructors = probeConstructors(javaType);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getSimpleClassName() {
        return simpleClassName;
    }

    public FieldMetadata[] getFields() {
        return fields;
    }

    public MethodMetadata[] getMethods() {
        return methods;
    }

    public ConstructorMetadata[] getConstructors() {
        return constructors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassMetadata that = (ClassMetadata) o;
        return Objects.equal(getPackageName(), that.getPackageName()) && Objects.equal(getSimpleClassName(), that.getSimpleClassName()) && Objects.equal(getFields(), that.getFields()) && Objects.equal(getMethods(), that.getMethods()) && Objects.equal(getConstructors(), that.getConstructors());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPackageName(), getSimpleClassName(), getFields(), getMethods(), getConstructors());
    }

    private ConstructorMetadata[] probeConstructors(Class<?> type) {
        Constructor<?>[] cons = type.getDeclaredConstructors();
        List<ConstructorMetadata> metadata = new LinkedList<>();
        ConstructorMetadata primary = null;
        for (Constructor<?> con : cons) {
            ConstructorMetadata constructorMetadata = new ConstructorMetadata(con);
            if (primary == null) {
                primary = constructorMetadata;
            } else if (constructorMetadata.isPrimary()) {
                throw new IllegalArgumentException("Only one primary constructor allow exist on class [" + type.getName() + "]");
            }
            metadata.add(constructorMetadata);
        }
        return metadata.isEmpty() ? null : metadata.toArray(new ConstructorMetadata[0]);
    }

    private MethodMetadata[] probeMethods(Class<?> type) {
        List<MethodMetadata> results = new LinkedList<>();

        addMethods(type, results, true);
        scanMethodsInSuperClass(type, results);

        return results.isEmpty() ? null : results.toArray(new MethodMetadata[0]);
    }

    private void addMethods(Class<?> type, Collection<MethodMetadata> results, boolean includePrivate) {
        Method[] methodArray;

        if (includePrivate) {
            methodArray = type.getDeclaredMethods();
        } else {
            methodArray = type.getMethods();
        }

        for (Method method : methodArray) {
            MethodMetadata metadata = new MethodMetadata(method);
            results.add(metadata);
        }
    }

    private void scanMethodsInSuperClass(Class<?> type, Collection<MethodMetadata> results) {
        Class<?> superClass = type.getSuperclass();

        if (superClass != null) {
            addMethods(superClass, results, false);
            scanMethodsInSuperClass(superClass, results);
        }
    }

    private FieldMetadata[] probeFields(Class<?> type) {
        List<FieldMetadata> results = new LinkedList<>();

        addFields(type, results, true);
        scanFieldsInSuperCLass(type, results);

        return results.isEmpty() ? null : results.toArray(new FieldMetadata[0]);
    }

    private void addFields(Class<?> type, Collection<FieldMetadata> results, boolean includePrivate) {
        Field[] fieldArray;

        if (includePrivate) {
            fieldArray = type.getDeclaredFields();
        } else {
            fieldArray = type.getFields();
        }

        for (Field field : fieldArray) {
            FieldMetadata fieldMetadata = new FieldMetadata(field);
            results.add(fieldMetadata);
        }
    }

    private void scanFieldsInSuperCLass(Class<?> type, Collection<FieldMetadata> results) {
        Class<?> superClass = type.getSuperclass();

        if (superClass != null) {
            addFields(superClass, results, false);
            scanFieldsInSuperCLass(superClass, results);
        }
    }
}
