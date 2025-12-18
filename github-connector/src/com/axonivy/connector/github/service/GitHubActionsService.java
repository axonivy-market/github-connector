package com.axonivy.connector.github.service;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.models.WorkflowRunAdvanced;

import ch.ivyteam.ivy.process.call.SubProcessCallResult;
import ch.ivyteam.ivy.process.call.SubProcessCallStartParamCaller;

public class GitHubActionsService extends AbstractGitHubService {
  private static final String GITHUB_ACTIONS_PROCESS = "GitHubActions";
  private static final String GH_ACTIONS_RUN_START = "getActionsRun";
  private static final String GH_ACTIONS_RUN_RESULT = "workflowRunAdvanced";
  private static GitHubActionsService instance;

  private GitHubActionsService() { }

  public static GitHubActionsService getInstance() {
    if (instance == null) {
      instance = new GitHubActionsService();
    }
    return instance;
  }

  public WorkflowRunAdvanced getWorkflowRuns(String owner, String repo, int page, int pageSize) {
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams(GH_ACTIONS_RUN_START, page, pageSize);
    caller.withParam(GitHubConstants.OWNER, owner);
    caller.withParam(GitHubConstants.REPO, repo);
    SubProcessCallResult result = caller.call();
    return result.get(GH_ACTIONS_RUN_RESULT, WorkflowRunAdvanced.class);
  }

  @Override
  protected String getProcessName() {
    return GITHUB_ACTIONS_PROCESS;
  }

}
