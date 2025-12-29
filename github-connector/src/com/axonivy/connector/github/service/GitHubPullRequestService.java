package com.axonivy.connector.github.service;

import com.axonivy.connector.github.constant.GitHubParamConstants;
import com.axonivy.connector.github.models.PullRequestAdvanced;

import ch.ivyteam.ivy.process.call.SubProcessCallStartParamCaller;

public class GitHubPullRequestService extends AbstractGitHubService {
  public static final String PULL_PROCESS = "GitHubPullRequest";
  public static final String GET_PULL_REQUEST_START = "getPullRequests";
  public static final String GET_PULL_REQUEST_RESULT = "pullRequestAdvanced";
  private static GitHubPullRequestService instance;

  private GitHubPullRequestService() { }

  public static GitHubPullRequestService getInstance() {
    if (instance == null) {
      instance = new GitHubPullRequestService();
    }
    return instance;
  }

  public PullRequestAdvanced getPullRequests(String owner, String repo, int page, int pageSize) {
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams(GET_PULL_REQUEST_START, page, pageSize);
    caller.withParam(GitHubParamConstants.OWNER, owner);
    caller.withParam(GitHubParamConstants.REPO, repo);
    return caller.call().get(GET_PULL_REQUEST_RESULT, PullRequestAdvanced.class);
  }

  @Override
  protected String getProcessName() {
    return PULL_PROCESS;
  }

}
