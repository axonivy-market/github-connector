package com.axonivy.connector.github.util;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.enums.Variable;

import ch.ivyteam.ivy.environment.Ivy;

public class VariableUtils {

  private VariableUtils() {
  }

  public static String getDefaultOrg() {
    return Ivy.var().get(Variable.ORG.getKey());
  }

  public static String getVariable(Variable variable) {
    if (variable == null) {
      return StringUtils.EMPTY;
    }
    return Ivy.var().get(variable.getKey());
  }

  public static void setVariable(Variable variable, String value) {
    if (variable == null) {
      return;
    }
    Ivy.var().set(variable.getKey(), value);
  }
}
