package com.axonivy.connector.github;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.axonivy.connector.github.models.RepoAdvanced;
import com.axonivy.connector.github.models.RepositoriesWithCount;
import com.axonivy.connector.github.wrappers.GithubApiRest;
import ch.ivyteam.ivy.jsf.primefaces.legazy.LazyDataModel7;

@SuppressWarnings("deprecation")
public class ReposModel extends LazyDataModel7<RepoAdvanced> {

  private static final long serialVersionUID = -6946640556684919260L;
  private boolean showDetailedPullRequests = false;
  private boolean showWorkflowRunStatus = true;

  @Override
  public String getRowKey(RepoAdvanced repo) {
    return repo.getId().toString();
  }

  @Override
  public List<RepoAdvanced> load(int first, int pageSize, String sortField, SortOrder sortOrder,
          Map<String, Object> filters) {
    if (first == 0) {
      first = 1;
    }
    var restClient = new GithubApiRest().perPage(pageSize).page((first / pageSize) + 1);
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
