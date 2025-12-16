package com.axonivy.connector.github.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.axonivy.connector.github.models.criteria.SearchIssueCriteria;
import com.axonivy.connector.github.service.GitHubIssueService;
import com.axonivy.connector.github.util.VariableUtils;
import com.github.api.client.Issue;

public class IssuesModel extends LazyDataModel<Issue> {

  private static final long serialVersionUID = 1L;
  private SearchIssueCriteria criteria;
  private GitHubIssueService service = GitHubIssueService.getInstance();

  public IssuesModel(SearchIssueCriteria criteria) {
    super();
    this.criteria = criteria;
  }

  @Override
  public String getRowKey(Issue issue) {
    return issue.getId().toString();
  }

  @Override
  public List<Issue> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
    if (first == 0) {
      first = 1;
    }

    List<Issue> issues = new ArrayList<>();
    IssueAdvanced issueAdvanced = service.searchIssuesByCriteria(criteria, first, pageSize);
    if (issueAdvanced != null) {
      setRowCount(issueAdvanced.getTotalCountValue().intValue());
      issues = issueAdvanced.getIssueItems();
    } else {
      setRowCount(0);
    }
    
    return issues;
  }

  @Override
  public int count(Map<String, FilterMeta> filterBy) {
    SearchIssueCriteria criteria = SearchIssueCriteria.builder()
        .org(VariableUtils.getDefaultOrg())
        .stateOpen()
        .build();
    return service.countIssuesByCriteria(criteria).intValue();
  }

  public void search(SearchIssueCriteria searchIssueCriteria) {
    this.criteria = searchIssueCriteria;
    this.load(1, getPageSize(), Map.of(), Map.of());
  }

}
