package com.axonivy.connector.github.service;

import java.util.List;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.converter.JSONConverter;
import com.axonivy.connector.github.models.RepositorySearch;
import com.axonivy.connector.github.models.criteria.SearchRepositoryCriteria;
import com.github.api.client.Repository;

import ch.ivyteam.ivy.process.call.SubProcessCallResult;
import ch.ivyteam.ivy.process.call.SubProcessCallStartParamCaller;

public class GitHubRepoService extends AbstractGitHubService {
  public static final String GITHUB_REPO_PROCESS = "GitHubRepo";
  public static final String GH_SEARCH_REPO_START = "searchRepos";
  public static final String GH_GET_USER_REPO = "getUserRepos";
  private static GitHubRepoService instance;

  private GitHubRepoService() { }

  public static GitHubRepoService getInstance() {
    if (instance == null) {
      instance = new GitHubRepoService();
    }
    return instance;
  }
  
  public RepositorySearch searchRepositoriesByCriteria(SearchRepositoryCriteria criteria, int page, int pageSize) {
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams(GH_SEARCH_REPO_START, page, pageSize);
    SubProcessCallResult result = caller.withParam(GitHubConstants.QUERY, criteria.getQuery()).call();
    return result.get("repositorySearch", RepositorySearch.class);
  }
  
  public List<Repository> fetchReposWithCount(int page, int pageSize) {
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams(GH_GET_USER_REPO, page, pageSize);
    SubProcessCallResult result = caller.call();
    return JSONConverter.convertToList(result.get("repos"), Repository.class);
  }

  @Override
  protected String getProcessName() {
    return GITHUB_REPO_PROCESS;
  }

}
