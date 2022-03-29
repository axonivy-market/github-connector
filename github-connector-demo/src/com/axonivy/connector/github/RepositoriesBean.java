package com.axonivy.connector.github;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RepositoriesBean {

  private ReposModel reposModel;

  public RepositoriesBean() {
    reposModel = new ReposModel();
  }

  public ReposModel getReposModel() {
    return reposModel;
  }

  public boolean isShowDetailedPullRequests() {
    return reposModel.isShowDetailedPullRequests();
  }

  public void setShowDetailedPullRequests(boolean showDetailedPullRequests) {
    reposModel.setShowDetailedPullRequests(showDetailedPullRequests);
  }

  public boolean isShowWorkflowRunStatus() {
    return reposModel.isShowWorkflowRunStatus();
  }

  public void setShowWorkflowRunStatus(boolean showWorkflowRunStatus) {
    reposModel.setShowWorkflowRunStatus(showWorkflowRunStatus);
  }

  public String getIssuesColumnTitle() {
    if (isShowDetailedPullRequests()) {
      return "Issues";
    }
    return "Issues/Pull requests";
  }

  public String getIssuesColumnWidth() {
    if (isShowDetailedPullRequests()) {
      return "10%";
    }
    return "20%";
  }

}
