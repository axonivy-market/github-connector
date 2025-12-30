package com.axonivy.connector.github.models;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.axonivy.connector.github.converter.JSONConverter;
import com.axonivy.connector.github.models.criteria.SearchRepositoryCriteria;
import com.axonivy.connector.github.service.GitHubActionsService;
import com.axonivy.connector.github.service.GitHubPullRequestService;
import com.axonivy.connector.github.service.GitHubRepoService;
import com.github.api.client.Repository;

public class ReposModel extends LazyDataModel<RepositoryAdvanced> {

  private static final long serialVersionUID = -6946640556684919260L;
  private SearchRepositoryCriteria criteria;
  private boolean showDetailedPullRequests = false;
  private boolean showWorkflowRunStatus = true;
  private GitHubRepoService gitHubRepoService = GitHubRepoService.getInstance();
  private GitHubActionsService gitHubActionsService = GitHubActionsService.getInstance();
  private GitHubPullRequestService gitHubPullRequestService = GitHubPullRequestService.getInstance();

  public ReposModel(SearchRepositoryCriteria criteria) {
    super();
    this.criteria = criteria;
  }

  @Override
  public String getRowKey(RepositoryAdvanced repo) {
    return repo.getId().toString();
  }

  @Override
  public List<RepositoryAdvanced> load(int first, int pageSize, Map<String, SortMeta> sortBy,
      Map<String, FilterMeta> filterBy) {
    if (first == 0) {
      first = 1;
    }
    RepositorySearch repositorySearch = gitHubRepoService.searchRepositoriesByCriteria(criteria, first, pageSize);
    List<RepositoryAdvanced> repositories = repositorySearch.getRepoItems().stream()
        .map(repo -> copyPropertiesForRepo(repo)).toList();
    if (showWorkflowRunStatus) {
      repositories.forEach(repo -> fetchWorkflowRunsForRepo(repo));
    }
    if (showDetailedPullRequests) {
      repositories.forEach(repo -> fetchPullRequestForRepo(repo));
    }

    setRowCount(repositorySearch.getTotalCountValue().intValue());
    return repositories;
  }

  private RepositoryAdvanced copyPropertiesForRepo(Repository repo) {
     return JSONConverter.convertToClass(repo, RepositoryAdvanced.class);
  }

  private void fetchPullRequestForRepo(RepositoryAdvanced repo) {
    PullRequestAdvanced pullRequestAdvanced = gitHubPullRequestService
        .getPullRequests(repo.getOwner().getLogin().toString(), repo.getName().toString(), 1, 1);
    repo.setPullRequestAdvanced(pullRequestAdvanced);
  }

  private void fetchWorkflowRunsForRepo(RepositoryAdvanced repo) {
    WorkflowRunAdvanced workflowRunAdvanced = gitHubActionsService
        .getWorkflowRuns(repo.getOwner().getLogin().toString(), repo.getName().toString(), 1, 100);
    repo.setWorkflowRunAdvanced(workflowRunAdvanced);
  }

  @Override
  public int count(Map<String, FilterMeta> filterBy) {
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
