package com.axonivy.connector.github.enums;

public enum Variable {
  ORG("com.axonivy.connector.github.org"),
  SEARCH_ISSUE_CRITERIA("com.axonivy.connector.github.scanner.searchIssueCriteria");
  private String key;
  
  Variable(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
