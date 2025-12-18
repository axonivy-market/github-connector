package com.axonivy.connector.github.managedbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.axonivy.connector.github.models.ReposModel;
import com.axonivy.connector.github.models.criteria.SearchRepositoryCriteria;
import com.axonivy.connector.github.util.VariableUtils;

@ManagedBean
@ViewScoped
public class RepositoriesBean {

  private ReposModel reposModel;

  public RepositoriesBean() {
    String orgName = VariableUtils.getDefaultOrg();
    var criteria = SearchRepositoryCriteria.builder()
        .org(orgName)
        .isPublic()
        .build();
    reposModel = new ReposModel(criteria);
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

}
