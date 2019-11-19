package com.google.api.services.sheets.v4.model;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.json.GenericJson;

public final class AddFilterViewRequest extends GenericJson
{
    @Key
    private FilterView filter;
    
    public AddFilterViewRequest() {
        super();
    }
    
    public FilterView getFilter() {
        return this.filter;
    }
    
    public AddFilterViewRequest setFilter(final FilterView filter) {
        this.filter = filter;
        return this;
    }
    
    @Override
    public AddFilterViewRequest set(final String s, final Object o) {
        return (AddFilterViewRequest)super.set(s, o);
    }
    
    @Override
    public AddFilterViewRequest clone() {
        return (AddFilterViewRequest)super.clone();
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
