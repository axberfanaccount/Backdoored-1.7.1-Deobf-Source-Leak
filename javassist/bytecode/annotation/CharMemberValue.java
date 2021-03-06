package javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.bytecode.ConstPool;

public class CharMemberValue extends MemberValue
{
    int valueIndex;
    
    public CharMemberValue(final int valueIndex, final ConstPool constPool) {
        super('C', constPool);
        this.valueIndex = valueIndex;
    }
    
    public CharMemberValue(final char value, final ConstPool constPool) {
        super('C', constPool);
        this.setValue(value);
    }
    
    public CharMemberValue(final ConstPool constPool) {
        super('C', constPool);
        this.setValue('\0');
    }
    
    @Override
    Object getValue(final ClassLoader classLoader, final ClassPool classPool, final Method method) {
        return new Character(this.getValue());
    }
    
    @Override
    Class getType(final ClassLoader classLoader) {
        return Character.TYPE;
    }
    
    public char getValue() {
        return (char)this.cp.getIntegerInfo(this.valueIndex);
    }
    
    public void setValue(final char c) {
        this.valueIndex = this.cp.addIntegerInfo(c);
    }
    
    @Override
    public String toString() {
        return Character.toString(this.getValue());
    }
    
    @Override
    public void write(final AnnotationsWriter annotationsWriter) throws IOException {
        annotationsWriter.constValueIndex(this.getValue());
    }
    
    @Override
    public void accept(final MemberValueVisitor memberValueVisitor) {
        memberValueVisitor.visitCharMemberValue(this);
    }
}
