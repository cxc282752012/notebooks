package com.books.notebasecore.util;

import java.io.Serializable;
import java.util.Hashtable;

public class BaseEntity implements Serializable {

    /** @Fields serialVersionUID: */

    private static final long serialVersionUID = -2113524213108527884L;

    /** @Fields serialVersionUID: */

    private Hashtable<String, Object> baseData = new Hashtable<String, Object>();

    public Hashtable<String, Object> getBaseData() {
        return baseData;
    }

    public void setBaseData(Hashtable<String, Object> baseData) {
        this.baseData = baseData;
    }

    public void setBaseKV(String key, Object value) {
        this.baseData.put(key, value);
    }

    public Object getBaseKV(String key) {
        return this.baseData.get(key);
    }

}
