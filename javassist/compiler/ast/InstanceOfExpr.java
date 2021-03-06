package javassist.compiler.ast;

import javassist.compiler.CompileError;

public class InstanceOfExpr extends CastExpr
{
    public InstanceOfExpr(final ASTList list, final int n, final ASTree asTree) {
        super(list, n, asTree);
    }
    
    public InstanceOfExpr(final int n, final int n2, final ASTree asTree) {
        super(n, n2, asTree);
    }
    
    @Override
    public String getTag() {
        return "instanceof:" + this.castType + ":" + this.arrayDim;
    }
    
    @Override
    public void accept(final Visitor visitor) throws CompileError {
        visitor.atInstanceOfExpr(this);
    }
}
