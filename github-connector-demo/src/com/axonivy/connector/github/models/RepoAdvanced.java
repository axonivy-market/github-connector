package com.axonivy.connector.github.models;

import java.util.List;
import java.util.stream.Collectors;

import com.axonivy.connector.github.wrappers.GithubApiRest;
import com.github.api.client.InlineResponse20019;
import com.github.api.client.Repository;

public class RepoAdvanced extends Repository {

  private Repository repo;
  private PullRequestsWithCount pullRequests;
  private InlineResponse20019 workflowRuns;

  public RepoAdvanced(Repository repo) {
    this(repo, true, true);
  }

  public RepoAdvanced(Repository repo, boolean workflows, boolean pullRequests) {
    this.repo = repo;
    if (workflows) {
      this.workflowRuns = new GithubApiRest().perPage(1).getWorkflowRuns(repo.getFullName());
    }
    if (pullRequests) {
      this.pullRequests = new GithubApiRest().perPage(1).getPullRequests(repo.getFullName());
    }
  }

  public void fetchWorkflowRuns() {
    this.workflowRuns = new GithubApiRest().perPage(1).getWorkflowRuns(repo.getFullName());
  }

  public void fetchPullRequests() {
    this.pullRequests = new GithubApiRest().perPage(1).getPullRequests(repo.getFullName());
  }

  public static List<RepoAdvanced> fromList(List<Repository> repos) {
    return createRepoAdvancedList(repos, false, false);
  }

  private static List<RepoAdvanced> createRepoAdvancedList(List<Repository> repos, boolean workflows, boolean pullRequests) {
    return repos.stream().map(repo -> new RepoAdvanced(repo, workflows, pullRequests)).collect(Collectors.toList());
  }

  public PullRequestsWithCount getPullRequests() {
    return pullRequests;
  }

  public int getPullRequestsCount() {
    return pullRequests.getCount();
  }

  public InlineResponse20019 getWorkflowruns() {
    return workflowRuns;
  }

  public Repository getRepo() {
    return repo;
  }

  public String getLastWorkflowRunStatus() {
    if (workflowRuns == null || workflowRuns.getWorkflowRuns().isEmpty()) {
      return "";
    }
    return workflowRuns.getWorkflowRuns().get(0).getConclusion();
  }
}
