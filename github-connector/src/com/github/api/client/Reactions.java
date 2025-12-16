package com.github.api.client;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

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
      Object v = values.get(key);
      if (v instanceof Number) {
          return ((Number) v).intValue();
      }
      return null;
  }

  @Override
  public String toString() {
      return "Reactions" + values;
  }
}
