package com.axonivy.connector.github.odata;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import ch.ivyteam.ivy.rest.client.mapper.JsonFeature;

public class OpenApiJsonFeature extends JsonFeature {

  @Override
  public boolean configure(FeatureContext context) {
    JacksonJsonProvider provider = new ODataMapperProvider();
    configure(provider, context.getConfiguration());
    context.register(provider, Priorities.ENTITY_CODER);
    return true;
  }

  public static class ODataMapperProvider extends JacksonJsonProvider {

    @SuppressWarnings("deprecation")
    @Override
    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType) {
      ObjectMapper mapper = super.locateMapper(type, mediaType);
      mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
      mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
      mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      // Ignore null value in JSON
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
      return mapper;
    }
  }
}
