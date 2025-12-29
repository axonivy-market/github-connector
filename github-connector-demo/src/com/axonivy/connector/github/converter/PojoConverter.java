package com.axonivy.connector.github.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("pojoConverter")
public class PojoConverter implements Converter {
  private static final String CONVERTER_IDENTIFIER = PojoConverter.class.getName();
  private static final String KEY = ":::";
  private static final String MAP_KEY_TEMPLATE = CONVERTER_IDENTIFIER + KEY + "%s" + KEY + "%s";

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object item) throws ConverterException {
    if (item != null && !isEmptyString(item)) {
      Map<String, Object> viewMap = getViewMap(context);
      String hash = String.valueOf(item.hashCode());
      String mapKey = String.format(MAP_KEY_TEMPLATE, component.getId(), hash);
      viewMap.put(mapKey, item);

      return hash;
    }
    return "";
  }

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String selectedvalue) {
    if (selectedvalue != null && selectedvalue.length() > 0) {

      String mapKey = String.format(MAP_KEY_TEMPLATE, component.getId(), selectedvalue);
      Map<String, Object> viewMap = getViewMap(context);

      return viewMap.get(mapKey);
    }
    return null;
  }

  private boolean isEmptyString(Object item) {
    return String.class.isAssignableFrom(item.getClass()) && "".equals(item);
  }

  private Map<String, Object> getViewMap(FacesContext context) {
    UIViewRoot viewRoot = context.getViewRoot();
    return viewRoot.getViewMap();
  }
}