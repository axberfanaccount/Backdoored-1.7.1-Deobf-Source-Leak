package org.json;

import java.util.Iterator;

class XML$1$1 implements Iterator<Integer> {
    private int nextIndex = 0;
    private int length = this.this$0.val$string.length();
    final /* synthetic */ XML$1 this$0;
    
    XML$1$1(final XML$1 this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public boolean hasNext() {
        return this.nextIndex < this.length;
    }
    
    @Override
    public Integer next() {
        final int codePoint = this.this$0.val$string.codePointAt(this.nextIndex);
        this.nextIndex += Character.charCount(codePoint);
        return codePoint;
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public /* bridge */ Object next() {
        return this.next();
    }
}