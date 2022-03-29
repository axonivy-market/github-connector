package com.axonivy.connector.github.models;

import java.util.List;

public class RepositoriesWithCount {

  private int count;
  private List<RepoAdvanced> repositories;

  public RepositoriesWithCount(int count) {
    this.count = count;
  }

  public RepositoriesWithCount(List<RepoAdvanced> repos, int count) {
    this.repositories = repos;
    this.count = count;
  }

  public List<RepoAdvanced> getRepositories() {
    return repositories;
  }

  public void setRepositories(List<RepoAdvanced> repositories) {
    this.repositories = repositories;
  }

  public int getCount() {
    return count;
  }

  public void fetchReposWorkflows() {
    for (RepoAdvanced repo : repositories) {
      repo.fetchWorkflowRuns();
    }
  }

  public void fetchReposPullRequests() {
    for (RepoAdvanced repo : repositories) {
      repo.fetchPullRequests();
    }
  }

}
