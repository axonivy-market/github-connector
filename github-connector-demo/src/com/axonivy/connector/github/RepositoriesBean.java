package com.axonivy.connector.github;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class RepositoriesBean {
  private ReposModel reposModel;

  public RepositoriesBean() {
    reposModel = new ReposModel();
  }

  public ReposModel getReposModel() {
    return reposModel;
  }

}
