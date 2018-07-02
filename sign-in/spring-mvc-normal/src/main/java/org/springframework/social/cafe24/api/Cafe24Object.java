package org.springframework.social.cafe24.api;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public abstract class Cafe24Object {
    private Map<String, Object> extraData;

    public Cafe24Object() {
        this.extraData = new HashMap<String, Object>();
    }

    /**
     * @return Any fields in response from Facebook that are otherwise not mapped to any properties.
     */
    public Map<String, Object> getExtraData() {
        return extraData;
    }

    /**
     * {@link JsonAnySetter} hook. Called when an otherwise unmapped property is being processed during JSON deserialization.
     * @param key The property's key.
     * @param value The property's value.
     */
    protected void add(String key, Object value) {
        extraData.put(key, value);
    }
}
