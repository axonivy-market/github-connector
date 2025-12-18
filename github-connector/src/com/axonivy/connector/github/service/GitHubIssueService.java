package com.axonivy.connector.github.service;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.converter.JSONConverter;
import com.axonivy.connector.github.models.IssueSearch;
import com.axonivy.connector.github.models.criteria.SearchIssueCriteria;
import com.axonivy.connector.github.util.VariableUtils;
import com.github.api.client.Issue;
import com.github.api.client.IssueComment;
import com.github.api.client.IssueNumberAssigneesBody;
import com.github.api.client.IssueNumberAssigneesBody1;
import com.github.api.client.IssuesIssueNumberBody;

import ch.ivyteam.ivy.process.call.SubProcessCallResult;
import ch.ivyteam.ivy.process.call.SubProcessCallStartParamCaller;

public class GitHubIssueService extends AbstractGitHubService {

  public static final String GITHUB_ISSUE_PROCESS = "GitHubIssue";
  public static final String GH_SEARCH_ISSUES_START = "searchIssues";
  public static final String GH_GET_ASSIGNED_ISSUES_START = "getAssignedIssues";
  public static final String GH_GET_ORG_ISSUES_START = "getOrgIssues";
  public static final String GH_ADD_COMMENT_START = "addComment";
  public static final String GH_ASSIGN_USER_START = "assignUser";
  public static final String GH_GET_COMMENTS_START = "getComments";
  public static final String GH_PATCH_ISSUE_START = "patchIssue";
  public static final String GH_LIST_ISSUES_RESULT = "issues";
  public static final String GH_ISSUES_SEARCH_RESULT = "issueSearch";
  public static final String GH_ISSUE_RESULT = "issue";
  public static final String ISSUE_NUMBER = "issueNumber";
  private static GitHubIssueService instance;

  private GitHubIssueService() {}

  public static GitHubIssueService getInstance() {
    if (instance == null) {
      instance = new GitHubIssueService();
    }
    return instance;
  }

  public List<Issue> getAssignedIssues(int page, int pageSize) {
    SubProcessCallResult result = createCallSubProcessWithDefaultParams(GH_GET_ASSIGNED_ISSUES_START, page, pageSize).call();
    return JSONConverter.convertToList(result.get(GH_LIST_ISSUES_RESULT), Issue.class);
  }

  public List<Issue> getOrgIssues(int page, int pageSize) {
    SubProcessCallResult result = createCallSubProcessWithDefaultParams(GH_GET_ORG_ISSUES_START, page, pageSize).call();
    return JSONConverter.convertToList(result.get(GH_LIST_ISSUES_RESULT), Issue.class);
  }

  public IssueNumberAssigneesBody buildAssigneeBodyRequest(List<String> usernames) {
    var assigneeBody = new IssueNumberAssigneesBody();
    if (CollectionUtils.isNotEmpty(usernames)) {
      assigneeBody.setAssignees(usernames);
    }
    return assigneeBody;
  }

  public IssueNumberAssigneesBody1 buildRemoveAssigneeBodyRequest(List<String> usernames) {
    var assigneeBody = new IssueNumberAssigneesBody1();
    if (CollectionUtils.isNotEmpty(usernames)) {
      assigneeBody.setAssignees(usernames);
    }
    return assigneeBody;
  }

  public IssueSearch searchIssuesByKeyword(String keyword, int page, int pageSize) {
    var criteriaBuilder = SearchIssueCriteria.builder()
        .org(VariableUtils.getDefaultOrg())
        .stateOpen();
    if (StringUtils.isNoneBlank(keyword)) {
      criteriaBuilder.keywords(keyword);
    }

    return searchIssuesByCriteria(criteriaBuilder.build(), page, pageSize);
  }

  public BigInteger countIssuesByCriteria(SearchIssueCriteria criteria) {
    if (criteria == null) {
      return BigInteger.ZERO;
    }

    IssueSearch result = searchIssuesByCriteria(criteria, 1, 10);
    return Objects.isNull(result) ? BigInteger.ZERO : result.getTotalCountValue();
  }

  public IssueComment addCommentToIssue(String owner, String repo, BigInteger issueNumber, String comment) {
    validateParams(owner, repo, issueNumber);
    var result =  createCallSubProcessWithStartPath(GH_ADD_COMMENT_START)
        .withParam(GitHubConstants.OWNER, owner)
        .withParam(GitHubConstants.REPO, repo)
        .withParam(ISSUE_NUMBER, issueNumber)
        .withParam("comment", comment)
        .call();
    IssueComment issueComment = result.get("issueComment", IssueComment.class);
    return issueComment;
  }

  public List<IssueComment> getIssueComments(String owner, String repo, BigInteger issueNumber) {
    validateParams(owner, repo, issueNumber);
    var result =  createCallSubProcessWithStartPath(GH_GET_COMMENTS_START)
        .withParam(GitHubConstants.OWNER, owner)
        .withParam(GitHubConstants.REPO, repo)
        .withParam(ISSUE_NUMBER, issueNumber)
        .call();
    List<IssueComment> issueComment = JSONConverter.convertListObjectsToNewList(result.get("issueComments"), IssueComment.class);
    return issueComment;
  }

  public IssueSearch searchIssuesByCriteria(SearchIssueCriteria criteria, int page, int pageSize) {
    if (criteria == null) {
      return null;
    }
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams(GH_SEARCH_ISSUES_START, page, pageSize);
    SubProcessCallResult result = caller.withParam(GitHubConstants.QUERY, criteria.getQuery()).call();
    return result.get(GH_ISSUES_SEARCH_RESULT, IssueSearch.class);
  }

  private void validateParams(String owner, String repo, BigInteger issueNumber) {
    ObjectUtils.requireNonEmpty(owner, "Owner must not be empty");
    ObjectUtils.requireNonEmpty(repo, "Repo must not be empty");
    ObjectUtils.requireNonEmpty(issueNumber, "IssueNumber must not be empty");
  }

  public Issue assignUsersToIssue(String owner, String repo, BigInteger issueNumber, List<String> usernames) {
    var usernamesParam = ch.ivyteam.ivy.scripting.objects.List.create(String.class, usernames);
    validateParams(owner, repo, issueNumber);
    var result =  createCallSubProcessWithStartPath(GH_ASSIGN_USER_START)
        .withParam(GitHubConstants.OWNER, owner)
        .withParam(GitHubConstants.REPO, repo)
        .withParam(ISSUE_NUMBER, issueNumber)
        .withParam("usernames", usernamesParam)
        .call();
    return result.get(GH_ISSUE_RESULT, Issue.class);
  }

  public Issue patchIssue(String owner, String repo, BigInteger issueNumber, IssuesIssueNumberBody issueNumberBody) {
    var result =  createCallSubProcessWithStartPath(GH_PATCH_ISSUE_START)
        .withParam(GitHubConstants.OWNER, owner)
        .withParam(GitHubConstants.REPO, repo)
        .withParam(ISSUE_NUMBER, issueNumber)
        .withParam("issueRequest", issueNumberBody)
        .call();
    return result.get(GH_ISSUE_RESULT, Issue.class);
  }

  @Override
  protected String getProcessName() {
    return GITHUB_ISSUE_PROCESS;
  }
}
