package com.github.api.client;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * This class to fix the OpenAPI swagger config
 * */
public class Reactions {
  private final Map<String, Object> values = new HashMap<>();

  @JsonAnySetter
  public void set(String key, Object value) {
      values.put(key, value);
  }

  @JsonAnyGetter
  public Map<String, Object> getValues() {
      return values;
  }

  public Object get(String key) {
      return values.get(key);
  }

  public Integer getInt(String key) {
      Object value = values.get(key);
      if (value instanceof Number) {
          return ((Number) value).intValue();
      }
      return null;
  }

  @Override
  public String toString() {
      return this.getClass().getSimpleName() + values;
  }
}
