package com.axonivy.connector.github.managedbean;

import java.io.Serializable;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;

import com.axonivy.connector.github.models.ReposModel;
import com.axonivy.connector.github.models.criteria.SearchRepositoryCriteria;
import com.axonivy.connector.github.util.VariableUtils;

@Named
@ViewScoped
public class RepositoriesBean implements Serializable {

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
