package org.javen.framework.core.type.reader

import org.javen.framework.core.type.filter.TypeFilter
import org.javen.framework.core.type.scanner.Scanner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CLassReaderTest {

    private val reader: ClassReader = ClassReader.forType(this::class)

    // ClassReader test case

    @Test
    fun readCLass_DoesNotThrowException() {
        Assertions.assertDoesNotThrow { reader.readClass() }
    }

    @Test
    fun readClass_DoesNotNull() {
        Assertions.assertNotNull(reader.readClass())
    }

    // ClassMetadata test case

    @Test
    fun getPackageName_DoesNotNull() {
        val classMetadata: ClassMetadata = reader.readClass()
        Assertions.assertNotNull(classMetadata.packageName)
    }

    @Test
    fun getPackageName_NormalCase() {
        val classMetadata: ClassMetadata = reader.readClass()
        Assertions.assertEquals("org.javen.framework.core.type.reader", classMetadata.packageName)
    }

    @Test
    fun getSimpleClassName_DoesNotNull() {
        val classMetadata: ClassMetadata = reader.readClass()
        Assertions.assertNotNull(classMetadata.simpleClassName)
    }

    @Test
    fun getSimpleClassName_NormalCase() {
        val classMetadata: ClassMetadata = reader.readClass()
        Assertions.assertEquals("CLassReaderTest", classMetadata.simpleClassName)
    }

    @Test
    fun getFields_DoesNotNull() {
        val classMetadata: ClassMetadata = reader.readClass()
        Assertions.assertNotNull(classMetadata.fields)
    }

    @Test
    fun getFields_IsNull() {
        val r = ClassReader.forType(TypeFilter::class)
        val classMetadata: ClassMetadata = r.readClass()
        Assertions.assertNull(classMetadata.fields)
    }

    @Test
    fun getMethods_DoesNotNull() {
        val classMetadata: ClassMetadata = reader.readClass()
        Assertions.assertNotNull(classMetadata.methods)
    }

    // @Test
    // In kotlin, the compiler auto generate some system methods so this case never cause
    fun getMethods_IsNull() {
        val r = ClassReader.forType(MethodNullDummy::class)
        val classMetadata: ClassMetadata = r.readClass()
        Assertions.assertNull(classMetadata.methods)
    }

    @Test
    fun getConstructors_IsNull() {
        val r = ClassReader.forType(MethodNullDummy::class)
        val classMetadata: ClassMetadata = r.readClass()
        Assertions.assertNull(classMetadata.constructors)
    }

    // MethodMetadata test case

    @Test
    fun getMethodName_DoesNotNull() {
        val classMetadata: ClassMetadata = reader.readClass()
        val methodMetadata: MethodMetadata = classMetadata.methods.get(0)
        Assertions.assertNotNull(methodMetadata)
    }

    @Test
    fun getMethodName_NormalCase() {
        val classMetadata: ClassMetadata = ClassReader.forType(MethodNameDummy::class).readClass()
        val methodMetadata: MethodMetadata = classMetadata.methods[0]
        Assertions.assertEquals("test", methodMetadata.methodName)
    }

    @Test
    fun getMethodReturnType_IsNull() {
        val classMetadata: ClassMetadata = reader.readClass()
        val methodMetadata: MethodMetadata = classMetadata.methods.get(0)
        Assertions.assertNull(methodMetadata.methodReturnType)
    }

    @Test
    fun getMethodReturnType_DoesNotNull() {
        val r = ClassReader.forType(Scanner::class)
        val classMetadata: ClassMetadata = r.readClass()
        val methodMetadata: MethodMetadata = classMetadata.methods[0]
        Assertions.assertNotNull(methodMetadata.methodReturnType)
    }

    @Test
    fun getMethodReturnType_NormalCase() {
        val r = ClassReader.forType(Scanner::class)
        val classMetadata: ClassMetadata = r.readClass()
        val methodMetadata: MethodMetadata = classMetadata.methods[1]
        Assertions.assertEquals("org.javen.framework.core.type.scanner.ScanResult", methodMetadata.methodReturnType)
    }

    @Test
    fun isVoid_IsTrue() {
        val classMetadata: ClassMetadata = reader.readClass()
        val methodMetadata: MethodMetadata = classMetadata.methods.get(0)
        Assertions.assertEquals(true, methodMetadata.isVoid)
    }

    @Test
    fun isVoid_IsFalse() {
        val r = ClassReader.forType(Scanner::class)
        val classMetadata: ClassMetadata = r.readClass()
        val methodMetadata: MethodMetadata = classMetadata.methods[0]
        Assertions.assertEquals(false, methodMetadata.isVoid)
    }

    @Test
    fun getMethodParams_IsNull() {
        val classMetadata: ClassMetadata = reader.readClass()
        val methodMetadata: MethodMetadata = classMetadata.methods.get(0)
        Assertions.assertNull(methodMetadata.methodParams)
    }

    @Test
    fun getMethodParams_DoesNotNull() {
        val r = ClassReader.forType(Scanner::class)
        val classMetadata: ClassMetadata = r.readClass()
        val methodMetadata: MethodMetadata = classMetadata.methods[1]
        Assertions.assertNotNull(methodMetadata.methodParams)
    }

    @Test
    fun getMethodParams_NormalCase() {
        val r = ClassReader.forType(Scanner::class)
        val classMetadata: ClassMetadata = r.readClass()
        val methodMetadata: MethodMetadata = classMetadata.methods[2]
        Assertions.assertEquals("org.javen.framework.core.type.filter.TypeFilter", methodMetadata.methodParams?.get(0))
    }

    // FieldMetadata test case

    @Test
    fun getFieldName_DoesNotNull() {
        val classMetadata: ClassMetadata = reader.readClass()
        val fieldMetadata: FieldMetadata = classMetadata.fields[0]
        Assertions.assertNotNull(fieldMetadata.fieldName)
    }

    @Test
    fun getFieldName_NormalCase() {
        val classMetadata: ClassMetadata = reader.readClass()
        val fieldMetadata: FieldMetadata = classMetadata.fields.get(0)
        Assertions.assertEquals("reader", fieldMetadata.fieldName)
    }

    @Test
    fun getFieldType_DoesNotNull() {
        val classMetadata: ClassMetadata = reader.readClass()
        val fieldMetadata: FieldMetadata = classMetadata.fields.get(0)
        Assertions.assertNotNull(fieldMetadata.fieldType)
    }

    @Test
    fun getFieldType_NormalCase() {
        val classMetadata: ClassMetadata = reader.readClass()
        val fieldMetadata: FieldMetadata = classMetadata.fields.get(0)
        Assertions.assertEquals("org.javen.framework.core.type.reader.ClassReader", fieldMetadata.fieldType)
    }

    private class MethodNullDummy

    private class MethodNameDummy {
        fun test() {}
    }
}