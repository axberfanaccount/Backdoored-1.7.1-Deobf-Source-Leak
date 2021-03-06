package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.Mark;

public final class KeyToken extends Token
{
    public KeyToken(final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
    }
    
    @Override
    public ID getTokenId() {
        return ID.Key;
    }
}
