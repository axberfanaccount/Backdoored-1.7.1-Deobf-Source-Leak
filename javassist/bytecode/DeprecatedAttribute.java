package javassist.bytecode;

import java.util.Map;
import java.io.IOException;
import java.io.DataInputStream;

public class DeprecatedAttribute extends AttributeInfo
{
    public static final String tag = "Deprecated";
    
    DeprecatedAttribute(final ConstPool constPool, final int n, final DataInputStream dataInputStream) throws IOException {
        super(constPool, n, dataInputStream);
    }
    
    public DeprecatedAttribute(final ConstPool constPool) {
        super(constPool, "Deprecated", new byte[0]);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool constPool, final Map map) {
        return new DeprecatedAttribute(constPool);
    }
}
