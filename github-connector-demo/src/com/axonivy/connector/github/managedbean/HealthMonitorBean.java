package com.axonivy.connector.github.managedbean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.converter.JSONConverter;
import com.axonivy.connector.github.models.PullRequestAdvanced;
import com.axonivy.connector.github.models.RepositoryAdvanced;
import com.axonivy.connector.github.models.RepositorySearch;
import com.axonivy.connector.github.models.WorkflowRunAdvanced;
import com.axonivy.connector.github.models.criteria.SearchRepositoryCriteria;
import com.axonivy.connector.github.service.GitHubActionsService;
import com.axonivy.connector.github.service.GitHubPullRequestService;
import com.axonivy.connector.github.service.GitHubRepoService;
import com.axonivy.connector.github.util.VariableUtils;
import com.github.api.client.Repository;

@ManagedBean
@SessionScoped
public class HealthMonitorBean {

  private GitHubRepoService gitHubRepoService = GitHubRepoService.getInstance();
  private GitHubActionsService gitHubActionsService = GitHubActionsService.getInstance();
  private GitHubPullRequestService gitHubPullRequestService = GitHubPullRequestService.getInstance();
  private static Date lastReposAccess;
  private List<RepositoryAdvanced> cachedRepos;
  private SearchRepositoryCriteria criteria;

  @PostConstruct
  public void init() {
    String orgName = VariableUtils.getDefaultOrg();
    criteria = SearchRepositoryCriteria.builder()
        .org(orgName)
        .isPublic()
        .build();
  }
  
  /**
   * Refresh the list if the last Repos access happened
   * to occur more than ten minutes before
   * @return repositories
  */
  public List<RepositoryAdvanced> getRepos() {
    int tenMinutes = 600000;
    if (lastReposAccess == null || (new Date().getTime() - lastReposAccess.getTime()) > tenMinutes) {
      searchReposWithWorkflowRunsAndPullRequests();
      lastReposAccess = new Date();
    }
    return cachedRepos;
  }

  public void forceRefresh() {
    searchReposWithWorkflowRunsAndPullRequests();
    lastReposAccess = new Date();
  }

  private void searchReposWithWorkflowRunsAndPullRequests() {
    RepositorySearch repositorySearch = gitHubRepoService.searchRepositoriesByCriteria(criteria, 1, GitHubConstants.MAX_PAGE_SIZE);
    List<RepositoryAdvanced> repositories = repositorySearch.getRepoItems().stream()
        .map(repo -> cloneRepositoryToAdvanced(repo)).toList();
    repositories.forEach(repo -> fetchWorkflowRunsForRepo(repo));
    repositories.forEach(repo -> fetchPullRequestForRepo(repo));
    cachedRepos = repositories;
  }

  private RepositoryAdvanced cloneRepositoryToAdvanced(Repository repo) {
    return JSONConverter.convertToClass(repo, RepositoryAdvanced.class);
  }

  private void fetchPullRequestForRepo(RepositoryAdvanced repo) {
    PullRequestAdvanced pullRequestAdvanced = gitHubPullRequestService
        .getPullRequests(repo.getOwner().getLogin().toString(), repo.getName().toString(), 1, GitHubConstants.MAX_PAGE_SIZE);
    repo.setPullRequestAdvanced(pullRequestAdvanced);
  }

  private void fetchWorkflowRunsForRepo(RepositoryAdvanced repo) {
    WorkflowRunAdvanced workflowRunAdvanced = gitHubActionsService
        .getWorkflowRuns(repo.getOwner().getLogin().toString(), repo.getName().toString(), 1, GitHubConstants.MAX_PAGE_SIZE);
    repo.setWorkflowRunAdvanced(workflowRunAdvanced);
  }

  public String getLastReposAccess() {
    if (lastReposAccess == null) {
      return new Date().toString();
    }
    return lastReposAccess.toString();
  }
}
