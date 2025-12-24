package com.axonivy.connector.github.service;

import java.util.List;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.converter.JSONConverter;
import com.axonivy.connector.github.models.RepositorySearch;
import com.axonivy.connector.github.models.criteria.SearchRepositoryCriteria;
import com.github.api.client.Repository;

import ch.ivyteam.ivy.process.call.SubProcessCallStartParamCaller;

public class GitHubRepoService extends AbstractGitHubService {
  public static final String REPO_PROCESS = "GitHubRepo";
  public static final String GET_USER_REPO_START = "getUserRepos";
  public static final String GET_USER_REPO_RESULT = "repos";
  public static final String SEARCH_REPO_START = "searchRepos";
  public static final String SERACH_REPO_RESULT = "repositorySearch";
  private static GitHubRepoService instance;

  private GitHubRepoService() { }

  public static GitHubRepoService getInstance() {
    if (instance == null) {
      instance = new GitHubRepoService();
    }
    return instance;
  }
  
  public RepositorySearch searchRepositoriesByCriteria(SearchRepositoryCriteria criteria, int page, int pageSize) {
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams(SEARCH_REPO_START, page, pageSize);
    caller.withParam(GitHubConstants.QUERY, criteria.getQuery());
    return caller.call().get(SERACH_REPO_RESULT, RepositorySearch.class);
  }
  
  public List<Repository> fetchReposWithCount(int page, int pageSize) {
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams(GET_USER_REPO_START, page, pageSize);
    return JSONConverter.convertToList(caller.call().get(GET_USER_REPO_RESULT), Repository.class);
  }

  @Override
  protected String getProcessName() {
    return REPO_PROCESS;
  }

}
