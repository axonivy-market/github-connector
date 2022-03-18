package com.axonivy.connector.github.models;

import java.util.List;

import com.github.api.client.Repository;

public class RepositoriesWithCount {

  private int count;
  private List<Repository> repositories;

  public RepositoriesWithCount(List<Repository> repos, int count) {
    this.repositories = repos;
    this.count = count;
  }

  public List<Repository> getRepositories() {
    return repositories;
  }

  public int getCount() {
    return count;
  }

}
