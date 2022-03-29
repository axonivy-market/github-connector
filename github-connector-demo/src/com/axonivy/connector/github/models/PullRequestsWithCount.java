package com.axonivy.connector.github.models;

import java.util.List;

import com.github.api.client.PullRequestSimple;

public class PullRequestsWithCount {

  private int count;
  private List<PullRequestSimple> pullRequests;

  public PullRequestsWithCount(int count, List<PullRequestSimple> pullRequests) {
     this.count = count;
     this.pullRequests = pullRequests;
  }

  public int getCount() {
    return count;
  }

  public List<PullRequestSimple> getPullRequests() {
    return pullRequests;
  }

}
