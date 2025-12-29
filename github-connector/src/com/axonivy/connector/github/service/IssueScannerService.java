package com.axonivy.connector.github.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.constant.GitHubParamConstants;
import com.axonivy.connector.github.converter.JSONConverter;
import com.axonivy.connector.github.enums.Variable;
import com.axonivy.connector.github.models.IssueSearch;
import com.axonivy.connector.github.models.criteria.SearchIssueCriteria;
import com.axonivy.connector.github.util.GitHubApiUtils;
import com.axonivy.connector.github.util.VariableUtils;
import com.github.api.client.Issue;
import com.github.api.client.IssuesIssueNumberBody;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCall;
import ch.ivyteam.ivy.process.call.SubProcessSearchFilter;
import ch.ivyteam.ivy.process.call.SubProcessSearchFilter.SearchScope;
import ch.ivyteam.ivy.security.exec.Sudo;

public class IssueScannerService {

  private static final String BUILD_PATCH_ISSUE_BODY_START = "buildPatchIssueBody(String,String,BigInteger)";
  private static IssueScannerService instance;

  private IssueScannerService() { }

  public static IssueScannerService getInstance() {
    if (instance == null) {
      instance = new IssueScannerService();
    }
    return instance;
  }
  
  public void scanIssues() {
    SearchIssueCriteria criteria = getSearchIssueCriteria();
    List<Issue> issues = new ArrayList<>();
    var issueService = GitHubIssueService.getInstance();
    var page = 0;
    var totalCount = BigInteger.ZERO;
    do {
      page++;
      IssueSearch issueSearch = issueService.searchIssuesByCriteria(criteria, page, GitHubConstants.MAX_PAGE_SIZE);
      totalCount = issueSearch.getTotalCountValue();
      issues = issueSearch.getIssueItems();
      for (var issue : issues) {
        var repoUrl = issue.getRepositoryUrl().toString();
        var owner = GitHubApiUtils.extractRepoOwner(repoUrl);
        var repoName = GitHubApiUtils.extractRepoName(repoUrl);
        BigInteger issueNumber = new BigInteger(issue.getNumber().toString());
        try {
          IssuesIssueNumberBody issueNumberBody = loadPatchIssueBody(owner, repoName, issueNumber);
          issueService.patchIssue(owner, repoName, issueNumber, issueNumberBody);
          Ivy.log().info("Patch issue {0} of {1}/{2} successfully", issueNumber, owner, repoName);
        } catch (Exception e) {
          Ivy.log().error("Cannot patch issue due to {0}", e, e.getMessage());
        }
      }
      issues.clear();
    } while (page * GitHubConstants.MAX_PAGE_SIZE < totalCount.intValue());
  }

  private SearchIssueCriteria getSearchIssueCriteria() {
    String configAsString = VariableUtils.getVariable(Variable.SEARCH_ISSUE_CRITERIA);
    return JSONConverter.convertToObject(configAsString, SearchIssueCriteria.class);
  }

  public IssuesIssueNumberBody loadPatchIssueBody(String owner, String repo, BigInteger issueNumber) {
    return Sudo.get(() -> {
      var filter = SubProcessSearchFilter.create().setSearchScope(SearchScope.SECURITY_CONTEXT)
          .setSignature(BUILD_PATCH_ISSUE_BODY_START).toFilter();

      // Find subprocess
      var subProcessStartList = SubProcessCall.find(filter);
      if (CollectionUtils.isEmpty(subProcessStartList)) {
        return null;
      }

      var issueBody = new IssuesIssueNumberBody();
      subProcessStartList.forEach(subProcessStart -> {
        var result = subProcessStart.withParam(GitHubParamConstants.OWNER, owner)
            .withParam(GitHubParamConstants.REPO, repo)
            .withParam(GitHubParamConstants.ISSUE_NUMBER, issueNumber)
            .call().get(GitHubIssueService.PATCH_ISSUE_BODY, IssuesIssueNumberBody.class);
        if (result != null) {
          issueBody.setAssignee(ObjectUtils.getIfNull(result.getAssignee(), issueBody.getAssignee()));
          issueBody.setAssignees(ObjectUtils.getIfNull(result.getAssignees(), issueBody.getAssignees()));
          issueBody.setBody(ObjectUtils.getIfNull(result.getBody(), issueBody.getBody()));
          issueBody.setLabels(ObjectUtils.getIfNull(result.getLabels(), issueBody.getLabels()));
          issueBody.setMilestone(ObjectUtils.getIfNull(result.getMilestone(), issueBody.getMilestone()));
          issueBody.setState(ObjectUtils.getIfNull(result.getState(), issueBody.getState()));
          issueBody.setStateReason(ObjectUtils.getIfNull(result.getStateReason(), issueBody.getStateReason()));
          issueBody.setTitle(ObjectUtils.getIfNull(result.getTitle(), issueBody.getTitle()));
        }
      });
      return issueBody;
    });
  }

}
