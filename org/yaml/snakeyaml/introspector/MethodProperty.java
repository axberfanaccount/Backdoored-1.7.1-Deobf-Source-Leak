package org.yaml.snakeyaml.introspector;

import org.yaml.snakeyaml.util.ArrayUtils;
import java.lang.annotation.Annotation;
import java.util.List;
import org.yaml.snakeyaml.error.YAMLException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.beans.PropertyDescriptor;

public class MethodProperty extends GenericProperty
{
    private final PropertyDescriptor property;
    private final boolean readable;
    private final boolean writable;
    
    private static Type discoverGenericType(final PropertyDescriptor propertyDescriptor) {
        final Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod != null) {
            return readMethod.getGenericReturnType();
        }
        final Method writeMethod = propertyDescriptor.getWriteMethod();
        if (writeMethod != null) {
            final Type[] genericParameterTypes = writeMethod.getGenericParameterTypes();
            if (genericParameterTypes.length > 0) {
                return genericParameterTypes[0];
            }
        }
        return null;
    }
    
    public MethodProperty(final PropertyDescriptor property) {
        super(property.getName(), property.getPropertyType(), discoverGenericType(property));
        this.property = property;
        this.readable = (property.getReadMethod() != null);
        this.writable = (property.getWriteMethod() != null);
    }
    
    @Override
    public void set(final Object o, final Object o2) throws Exception {
        if (!this.writable) {
            throw new YAMLException("No writable property '" + this.getName() + "' on class: " + o.getClass().getName());
        }
        this.property.getWriteMethod().invoke(o, o2);
    }
    
    @Override
    public Object get(final Object o) {
        try {
            this.property.getReadMethod().setAccessible(true);
            return this.property.getReadMethod().invoke(o, new Object[0]);
        }
        catch (Exception ex) {
            throw new YAMLException("Unable to find getter for property '" + this.property.getName() + "' on object " + o + ":" + ex);
        }
    }
    
    public List<Annotation> getAnnotations() {
        List<Annotation> list;
        if (this.isReadable() && this.isWritable()) {
            list = ArrayUtils.<Annotation>toUnmodifiableCompositeList(this.property.getReadMethod().getAnnotations(), this.property.getWriteMethod().getAnnotations());
        }
        else if (this.isReadable()) {
            list = ArrayUtils.<Annotation>toUnmodifiableList(this.property.getReadMethod().getAnnotations());
        }
        else {
            list = ArrayUtils.<Annotation>toUnmodifiableList(this.property.getWriteMethod().getAnnotations());
        }
        return list;
    }
    
    public <A extends Annotation> A getAnnotation(final Class<A> clazz) {
        Annotation annotation = null;
        if (this.isReadable()) {
            annotation = this.property.getReadMethod().<A>getAnnotation(clazz);
        }
        if (annotation == null && this.isWritable()) {
            annotation = this.property.getWriteMethod().<A>getAnnotation(clazz);
        }
        return (A)annotation;
    }
    
    @Override
    public boolean isWritable() {
        return this.writable;
    }
    
    @Override
    public boolean isReadable() {
        return this.readable;
    }
}
