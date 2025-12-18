package com.axonivy.connector.github.odata;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import ch.ivyteam.ivy.rest.client.mapper.JsonFeature;

public class OpenApiJsonFeature extends JsonFeature {

  private static final ObjectMapper ROOT_MAPPER = new ObjectMapper();

  @Override
  public boolean configure(FeatureContext context) {
    JacksonJsonProvider provider = new ODataMapperProvider();
    configure(provider, context.getConfiguration());
    context.register(provider, Priorities.ENTITY_CODER);
    return true;
  }

  public static class ODataMapperProvider extends JaxRsClientJson {

    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
        MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException {
      InputStream inputStream = unwrapValueRoot(entityStream);
      return super.readFrom(type, genericType, annotations, mediaType, httpHeaders, inputStream);
    }

    protected InputStream unwrapValueRoot(InputStream entityStream) throws IOException, JsonProcessingException {
      JsonNode node = ROOT_MAPPER.readTree(entityStream);
      String json = ROOT_MAPPER.writeValueAsString(node);
      return IOUtils.toInputStream(json, Charset.forName("UTF-8"));
    }

    @Override
    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType) {
      ObjectMapper mapper = super.locateMapper(type, mediaType);
      mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
      mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);

      return mapper;
    }
  }

  public static class JaxRsClientJson extends JacksonJsonProvider {
    @Override
    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType) {
      ObjectMapper mapper = super.locateMapper(type, mediaType);
      mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      return mapper;
    }
  }
}
