package com.axonivy.connector.github.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IssueLabelsRequest {

  @JsonProperty("labels")
  private List<String> labels = new ArrayList<>();

  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public IssueLabelsRequest addLabel(String label) {
    this.labels.add(label);
    return this;
  }
}