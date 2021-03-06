package javassist;

import javassist.compiler.JvstCodeGen;
import java.util.Hashtable;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.SyntheticAttribute;
import javassist.bytecode.AccessFlag;
import java.util.Map;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;

class CtNewWrappedMethod
{
    private static final String addedWrappedMethod = "_added_m$";
    
    CtNewWrappedMethod() {
        super();
    }
    
    public static CtMethod wrapped(final CtClass ctClass, final String s, final CtClass[] array, final CtClass[] exceptionTypes, final CtMethod ctMethod, final CtMethod.ConstParameter constParameter, final CtClass ctClass2) throws CannotCompileException {
        final CtMethod ctMethod2 = new CtMethod(ctClass, s, array, ctClass2);
        ctMethod2.setModifiers(ctMethod.getModifiers());
        try {
            ctMethod2.setExceptionTypes(exceptionTypes);
        }
        catch (NotFoundException ex) {
            throw new CannotCompileException(ex);
        }
        ctMethod2.getMethodInfo2().setCodeAttribute(makeBody(ctClass2, ctClass2.getClassFile2(), ctMethod, array, ctClass, constParameter).toCodeAttribute());
        return ctMethod2;
    }
    
    static Bytecode makeBody(final CtClass ctClass, final ClassFile classFile, final CtMethod ctMethod, final CtClass[] array, final CtClass ctClass2, final CtMethod.ConstParameter constParameter) throws CannotCompileException {
        final boolean static1 = Modifier.isStatic(ctMethod.getModifiers());
        final Bytecode bytecode = new Bytecode(classFile.getConstPool(), 0, 0);
        bytecode.setMaxStack(makeBody0(ctClass, classFile, ctMethod, static1, array, ctClass2, constParameter, bytecode));
        bytecode.setMaxLocals(static1, array, 0);
        return bytecode;
    }
    
    protected static int makeBody0(final CtClass ctClass, final ClassFile classFile, final CtMethod ctMethod, final boolean b, final CtClass[] array, final CtClass ctClass2, final CtMethod.ConstParameter constParameter, final Bytecode bytecode) throws CannotCompileException {
        if (!(ctClass instanceof CtClassType)) {
            throw new CannotCompileException("bad declaring class" + ctClass.getName());
        }
        bytecode.addAload(0);
        int compileParameterList = compileParameterList(bytecode, array, b ? 0 : 1);
        int compile;
        String s;
        if (constParameter == null) {
            compile = 0;
            s = CtMethod.ConstParameter.defaultDescriptor();
        }
        else {
            compile = constParameter.compile(bytecode);
            s = constParameter.descriptor();
        }
        checkSignature(ctMethod, s);
        String addBodyMethod;
        try {
            addBodyMethod = addBodyMethod((CtClassType)ctClass, classFile, ctMethod);
        }
        catch (BadBytecode badBytecode) {
            throw new CannotCompileException(badBytecode);
        }
        if (b) {
            bytecode.addInvokestatic(Bytecode.THIS, addBodyMethod, s);
        }
        else {
            bytecode.addInvokespecial(Bytecode.THIS, addBodyMethod, s);
        }
        compileReturn(bytecode, ctClass2);
        if (compileParameterList < compile + 2) {
            compileParameterList = compile + 2;
        }
        return compileParameterList;
    }
    
    private static void checkSignature(final CtMethod ctMethod, final String s) throws CannotCompileException {
        if (!s.equals(ctMethod.getMethodInfo2().getDescriptor())) {
            throw new CannotCompileException("wrapped method with a bad signature: " + ctMethod.getDeclaringClass().getName() + '.' + ctMethod.getName());
        }
    }
    
    private static String addBodyMethod(final CtClassType ctClassType, final ClassFile classFile, final CtMethod ctMethod) throws BadBytecode, CannotCompileException {
        final Hashtable hiddenMethods = ctClassType.getHiddenMethods();
        String string = hiddenMethods.get(ctMethod);
        if (string == null) {
            do {
                string = "_added_m$" + ctClassType.getUniqueNumber();
            } while (classFile.getMethod(string) != null);
            final ClassMap classMap = new ClassMap();
            classMap.put(ctMethod.getDeclaringClass().getName(), ctClassType.getName());
            final MethodInfo methodInfo = new MethodInfo(classFile.getConstPool(), string, ctMethod.getMethodInfo2(), classMap);
            methodInfo.setAccessFlags(AccessFlag.setPrivate(methodInfo.getAccessFlags()));
            methodInfo.addAttribute(new SyntheticAttribute(classFile.getConstPool()));
            classFile.addMethod(methodInfo);
            hiddenMethods.put(ctMethod, string);
            final CtMember.Cache hasMemberCache = ctClassType.hasMemberCache();
            if (hasMemberCache != null) {
                hasMemberCache.addMethod(new CtMethod(methodInfo, ctClassType));
            }
        }
        return string;
    }
    
    static int compileParameterList(final Bytecode bytecode, final CtClass[] array, final int n) {
        return JvstCodeGen.compileParameterList(bytecode, array, n);
    }
    
    private static void compileReturn(final Bytecode bytecode, final CtClass ctClass) {
        if (ctClass.isPrimitive()) {
            final CtPrimitiveType ctPrimitiveType = (CtPrimitiveType)ctClass;
            if (ctPrimitiveType != CtClass.voidType) {
                final String wrapperName = ctPrimitiveType.getWrapperName();
                bytecode.addCheckcast(wrapperName);
                bytecode.addInvokevirtual(wrapperName, ctPrimitiveType.getGetMethodName(), ctPrimitiveType.getGetMethodDescriptor());
            }
            bytecode.addOpcode(ctPrimitiveType.getReturnOp());
        }
        else {
            bytecode.addCheckcast(ctClass);
            bytecode.addOpcode(176);
        }
    }
}
