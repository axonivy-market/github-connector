package com.axonivy.connector.github.service;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.models.PullRequestAdvanced;

import ch.ivyteam.ivy.process.call.SubProcessCallResult;
import ch.ivyteam.ivy.process.call.SubProcessCallStartParamCaller;

public class GitHubPullRequestService extends AbstractGitHubService {
  public static final String GITHUB_PULL_PROCESS = "GitHubPulls";
  public static final String GH_GET_PULL_REQUEST_START = "getPullRequests";
  private static GitHubPullRequestService instance;

  private GitHubPullRequestService() { }

  public static GitHubPullRequestService getInstance() {
    if (instance == null) {
      instance = new GitHubPullRequestService();
    }
    return instance;
  }

  public PullRequestAdvanced getPullRequests(String owner, String repo, int page, int pageSize) {
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams(GH_GET_PULL_REQUEST_START, page, pageSize);
    caller.withParam(GitHubConstants.OWNER, owner);
    caller.withParam(GitHubConstants.REPO, repo);
    SubProcessCallResult result = caller.call();
    return result.get("pullRequestAdvanced", PullRequestAdvanced.class);
  }

  @Override
  protected String getProcessName() {
    return GITHUB_PULL_PROCESS;
  }

}
