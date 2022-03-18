package com.axonivy.connector.github;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.axonivy.connector.github.wrappers.GithubApiRest;
import com.github.api.client.Repository;

public class ReposModel extends LazyDataModel<Repository> {

  @Override
  public Object getRowKey(Repository repo) {
    return repo.getId();
  }

  @Override
  public List<Repository> load(int first, int pageSize, String sortField, SortOrder sortOrder,
          Map<String, Object> filters) {
    if (first == 0) {
      first = 1;
    }
    var restClient = new GithubApiRest().perPage(pageSize).page((first/pageSize) + 1);
    var repos = restClient.getRepos();

    setRowCount(repos.getCount());

    return repos.getRepositories();
  }
}
