package com.axonivy.connector.github.enums;

import java.util.stream.Stream;

public enum IssueState {
  OPEN("open"), CLOSED("closed"), MERGED("merged"), UNMERGED("unmerged");

  private String state;

  private IssueState(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }

  public static IssueState from(String searchState) {
    return Stream.of(values()).filter(state -> state.getState().equals(searchState)).findFirst().orElse(null);
  }
}
