package com.axonivy.connector.github.models;

import com.github.api.client.Repository;

public class RepositoryAdvanced extends Repository {

  private WorkflowRunAdvanced workflowRunAdvanced;
  private PullRequestAdvanced pullRequestAdvanced;

  public RepositoryAdvanced() { }

  public WorkflowRunAdvanced getWorkflowRunAdvanced() {
    return workflowRunAdvanced;
  }

  public void setWorkflowRunAdvanced(WorkflowRunAdvanced workflowRunAdvanced) {
    this.workflowRunAdvanced = workflowRunAdvanced;
  }

  public PullRequestAdvanced getPullRequestAdvanced() {
    return pullRequestAdvanced;
  }

  public void setPullRequestAdvanced(PullRequestAdvanced pullRequestAdvanced) {
    this.pullRequestAdvanced = pullRequestAdvanced;
  }

}
