package com.axonivy.connector.github.models;

import java.util.List;

import com.github.api.client.PullRequestSimple;

public class PullRequestAdvanced {

  private int totalCount;
  private List<PullRequestSimple> pullRequests;

  public PullRequestAdvanced() {}

  public PullRequestAdvanced(int totalCount, List<PullRequestSimple> pullRequests) {
    this.totalCount = totalCount;
    this.pullRequests = pullRequests;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public List<PullRequestSimple> getPullRequests() {
    return pullRequests;
  }

  public void setPullRequests(List<PullRequestSimple> pullRequests) {
    this.pullRequests = pullRequests;
  }

}
