package com.axonivy.connector.github.managedbean;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.axonivy.connector.github.models.RepoAdvanced;
import com.axonivy.connector.github.models.RepositoriesWithCount;
import com.axonivy.connector.github.service.GitHubRepoService;

@ManagedBean
@SessionScoped
public class HealthMonitorBean {

  private static Date lastReposAccess;

  private List<RepoAdvanced> cachedRepos;

  // Refresh the list if the last Repos access happened
  // to occur more than ten minutes before
  public List<RepoAdvanced> getRepos() {
    int tenMinutes = 600000;
    if (lastReposAccess == null || (new Date().getTime() - lastReposAccess.getTime()) > tenMinutes) {
      var reposCount = GitHubRepoService.getReposCount();
      var restClient = new GitHubRepoService().perPage(reposCount).page(1);
      RepositoriesWithCount repos = restClient.getRepos();
      repos.fetchReposPullRequests();
      repos.fetchReposWorkflows();
      cachedRepos = repos.getRepositories();
      lastReposAccess = new Date();
    }
    return cachedRepos;
  }

  public void forceRefresh() {
    var reposCount = GitHubRepoService.getReposCount();
    var restClient = new GitHubRepoService().perPage(reposCount).page(1);
    RepositoriesWithCount repos = restClient.getRepos();
    repos.fetchReposPullRequests();
    repos.fetchReposWorkflows();
    cachedRepos = repos.getRepositories();
    lastReposAccess = new Date();
  }

  public String getLastReposAccess() {
    if (lastReposAccess == null) {
      return new Date().toString();
    }
    return lastReposAccess.toString();
  }
}
