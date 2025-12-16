package com.axonivy.connector.github.models;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.axonivy.connector.github.service.GitHubRepoService;


public class ReposModel extends LazyDataModel<RepoAdvanced> {

  private static final long serialVersionUID = -6946640556684919260L;
  private boolean showDetailedPullRequests = false;
  private boolean showWorkflowRunStatus = true;

  @Override
  public String getRowKey(RepoAdvanced repo) {
    return repo.getId().toString();
  }

  @Override
  public List<RepoAdvanced> load(int first, int pageSize, Map<String, SortMeta> sortBy,
      Map<String, FilterMeta> filterBy) {
    if (first == 0) {
      first = 1;
    }
    var restClient = new GitHubRepoService().perPage(pageSize).page((first / pageSize) + 1);
    RepositoriesWithCount repos = restClient.getRepos();
    if (showDetailedPullRequests && showWorkflowRunStatus) {
      repos.fetchReposPullRequests();
      repos.fetchReposWorkflows();
    } else if (showWorkflowRunStatus) {
      repos.fetchReposWorkflows();
    } else if (showDetailedPullRequests) {
      repos.fetchReposPullRequests();
    }

    setRowCount(repos.getCount());

    return repos.getRepositories();
  }

  @Override
  public int count(Map<String, FilterMeta> filterBy) {
    // TODO Auto-generated method stub
    return 0;
  }

  public boolean isShowDetailedPullRequests() {
    return showDetailedPullRequests;
  }

  public void setShowDetailedPullRequests(boolean showDetailedPullRequests) {
    this.showDetailedPullRequests = showDetailedPullRequests;
  }

  public boolean isShowWorkflowRunStatus() {
    return showWorkflowRunStatus;
  }

  public void setShowWorkflowRunStatus(boolean showWorkflowRunStatus) {
    this.showWorkflowRunStatus = showWorkflowRunStatus;
  }

}
