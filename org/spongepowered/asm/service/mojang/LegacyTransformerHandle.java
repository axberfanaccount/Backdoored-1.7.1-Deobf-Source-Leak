package org.spongepowered.asm.service.mojang;

import javax.annotation.Resource;
import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.service.ILegacyClassTransformer;

class LegacyTransformerHandle implements ILegacyClassTransformer
{
    private final IClassTransformer transformer;
    
    LegacyTransformerHandle(final IClassTransformer transformer) {
        super();
        this.transformer = transformer;
    }
    
    @Override
    public String getName() {
        return this.transformer.getClass().getName();
    }
    
    @Override
    public boolean isDelegationExcluded() {
        return this.transformer.getClass().<Resource>getAnnotation(Resource.class) != null;
    }
    
    @Override
    public byte[] transformClassBytes(final String s, final String s2, final byte[] array) {
        return this.transformer.transform(s, s2, array);
    }
}
