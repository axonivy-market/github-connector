package com.axonivy.connector.github.enums;

public enum Variable {
  ORG("githubConnector.org"), SEARCH_ISSUE_CRITERIA("githubConnector.scanner.searchIssueCriteria");
  private String key;
  
  Variable(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
