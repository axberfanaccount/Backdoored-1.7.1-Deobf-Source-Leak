package com.google.api.services.sheets.v4.model;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.json.GenericJson;

public final class AddSheetRequest extends GenericJson
{
    @Key
    private SheetProperties properties;
    
    public AddSheetRequest() {
        super();
    }
    
    public SheetProperties getProperties() {
        return this.properties;
    }
    
    public AddSheetRequest setProperties(final SheetProperties properties) {
        this.properties = properties;
        return this;
    }
    
    @Override
    public AddSheetRequest set(final String s, final Object o) {
        return (AddSheetRequest)super.set(s, o);
    }
    
    @Override
    public AddSheetRequest clone() {
        return (AddSheetRequest)super.clone();
    }
    
    @Override
    public /* bridge */ GenericJson set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    @Override
    public /* bridge */ GenericJson clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData clone() {
        return this.clone();
    }
    
    @Override
    public /* bridge */ GenericData set(final String s, final Object o) {
        return this.set(s, o);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
