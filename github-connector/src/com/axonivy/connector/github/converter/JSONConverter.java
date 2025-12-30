package com.axonivy.connector.github.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ch.ivyteam.ivy.environment.Ivy;

public class JSONConverter {

  static ObjectMapper mapper;

  private JSONConverter() {}

  public static <T> List<T> convertListObjectsToNewList(Object entity, Class<T> clazz) {
    List<T> result = new ArrayList<>();
    if (entity instanceof List) {
      ((List<?>) entity).forEach(item -> {
        var converted = getMapper().convertValue(item, clazz);
        result.add(converted);
      });
    }
    return result;
  }

  public static <T> List<T> convertToList(Object entity, Class<T> clazz) {
    if (entity == null) {
      return List.of();
    }
    JavaType requestedJavaType = getMapper().getTypeFactory().constructCollectionType(List.class, clazz);
    return getMapper().convertValue(entity, requestedJavaType);
  }

  public static <T> List<T> convertWithTypeReference(Object entity, TypeReference<List<T>> typeRef) {
    if (entity == null) {
      return List.of();
    }
    return getMapper().convertValue(entity, typeRef);
  }

  public static <T> T convertToClass(Object entity, Class<T> clazz) {
    if (entity == null) {
      return null;
    }
    return getMapper().convertValue(entity, clazz);
  }

  public static String convertToString(Object entity) {
    try {
      return getMapper().writeValueAsString(entity);
    } catch (JsonProcessingException e) {
      Ivy.log().error("Cannot convert data", e);
    }
    return StringUtils.EMPTY;
  }

  public static <T> T convertToObject(String entity, Class<T> clazz) {
    try {
      return getMapper().readValue(entity, clazz);
    } catch (JsonProcessingException e) {
      Ivy.log().error("Cannot parse string to class", e.getMessage());
    }
    return null;
  }

  private static ObjectMapper getMapper() {
    if (mapper == null) {
      mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
      mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return mapper;
  }
}
