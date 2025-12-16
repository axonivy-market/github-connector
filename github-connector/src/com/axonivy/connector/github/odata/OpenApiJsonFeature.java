package com.axonivy.connector.github.odata;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.OffsetDateTime;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import ch.ivyteam.ivy.rest.client.mapper.JsonFeature;

/**
 * Custom Feature to handle ODATA specific JSON features
 * 
 * <ul>
 * <li>Remove or rename root entity</li>
 * <li>Load custom {@link SuccessFactorsTypeCustomizations}</li>
 * <li>Load custom {@link InnerListResolveVisitor}</li>
 * <li>Load custom {@link OffsetDateTimeSerializer}</li>
 * <li>Load custom {@link GitHubQueryStringFilter}</li>
 * </ul>
 * 
 * So let's handle them to make object transformation possible.
 * 
 * @author jpl
 * @since 10.0.2
 */
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
      InputStream inputStream = IOUtils.toInputStream(json, Charset.forName("UTF-8"));
      return inputStream;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType) {
      ObjectMapper mapper = super.locateMapper(type, mediaType);
      // odata provides fields starting with an upper case character!
      mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
      mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
      mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);

      return mapper;
    }
  }

  public static class JaxRsClientJson extends JacksonJsonProvider {
    @Override
    @SuppressWarnings("deprecation")
    public ObjectMapper locateMapper(Class<?> type, MediaType mediaType) {
      ObjectMapper mapper = super.locateMapper(type, mediaType);
      // match our generated jax-rs client beans: that contain JSR310 data types
      mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
      // allow fields starting with an upper case character (e.g. in ODATA specs)!
      mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
      // not sending this optional value seems to be lass prone to errors for some
      // remote services.
      mapper.setSerializationInclusion(Include.NON_NULL);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      return mapper;
    }
  }
}
