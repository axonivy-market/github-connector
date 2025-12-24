package com.axonivy.connector.github.service;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.constant.GitHubParamConstants;
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
  public static final String ISSUE_PROCESS = "GitHubIssue";
  public static final String SEARCH_ISSUES_START = "searchIssues";
  public static final String SEARCH_ISSUES_RESULT = "issueSearch";
  public static final String GET_ASSIGNED_ISSUES_START = "getAssignedIssues";
  public static final String GET_ORG_ISSUES_START = "getOrgIssues";
  public static final String ADD_COMMENT_START = "addComment";
  public static final String ADD_COMMENT_RESULT = "issueComment";
  public static final String ASSIGN_USER_START = "assignUser";
  public static final String GET_COMMENTS_START = "getComments";
  public static final String GET_COMMENTS_RESULT = "issueComments";
  public static final String PATCH_ISSUE_START = "patchIssue";
  public static final String ISSUES_RESULT = "issues";
  public static final String ISSUE_RESULT = "issue";
  public static final String PATCH_ISSUE_BODY = "patchIssueBody";
  private static GitHubIssueService instance;

  private GitHubIssueService() {}

  public static GitHubIssueService getInstance() {
    if (instance == null) {
      instance = new GitHubIssueService();
    }
    return instance;
  }

  public List<Issue> getAssignedIssues(int page, int pageSize) {
    SubProcessCallResult result = createCallSubProcessWithDefaultParams(GET_ASSIGNED_ISSUES_START, page, pageSize).call();
    return JSONConverter.convertToList(result.get(ISSUES_RESULT), Issue.class);
  }

  public List<Issue> getOrgIssues(int page, int pageSize) {
    SubProcessCallResult result = createCallSubProcessWithDefaultParams(GET_ORG_ISSUES_START, page, pageSize).call();
    return JSONConverter.convertToList(result.get(ISSUES_RESULT), Issue.class);
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
    SubProcessCallStartParamCaller caller = createCallSubProcessWithStartPath(ADD_COMMENT_START)
        .withParam(GitHubConstants.OWNER, owner)
        .withParam(GitHubConstants.REPO, repo)
        .withParam(GitHubParamConstants.ISSUE_NUMBER, issueNumber)
        .withParam(GitHubParamConstants.COMMENT, comment);
    return caller.call().get(ADD_COMMENT_RESULT, IssueComment.class);
  }

  public List<IssueComment> getIssueComments(String owner, String repo, BigInteger issueNumber) {
    validateParams(owner, repo, issueNumber);
    SubProcessCallStartParamCaller caller = createCallSubProcessWithStartPath(GET_COMMENTS_START)
        .withParam(GitHubConstants.OWNER, owner)
        .withParam(GitHubConstants.REPO, repo)
        .withParam(GitHubParamConstants.ISSUE_NUMBER, issueNumber);
    return JSONConverter.convertListObjectsToNewList(caller.call().get(GET_COMMENTS_RESULT), IssueComment.class);
  }

  public IssueSearch searchIssuesByCriteria(SearchIssueCriteria criteria, int page, int pageSize) {
    if (criteria == null) {
      return null;
    }
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams(SEARCH_ISSUES_START, page, pageSize);
    caller.withParam(GitHubConstants.QUERY, criteria.getQuery());
    return caller.call().get(SEARCH_ISSUES_RESULT, IssueSearch.class);
  }

  public Issue assignUsersToIssue(String owner, String repo, BigInteger issueNumber, List<String> usernames) {
    validateParams(owner, repo, issueNumber);
    var usernamesParam = ch.ivyteam.ivy.scripting.objects.List.create(String.class, usernames);
    SubProcessCallStartParamCaller caller =  createCallSubProcessWithStartPath(ASSIGN_USER_START)
        .withParam(GitHubConstants.OWNER, owner)
        .withParam(GitHubConstants.REPO, repo)
        .withParam(GitHubParamConstants.ISSUE_NUMBER, issueNumber)
        .withParam(GitHubParamConstants.USERNAMES, usernamesParam);
    return caller.call().get(ISSUE_RESULT, Issue.class);
  }

  public Issue patchIssue(String owner, String repo, BigInteger issueNumber, IssuesIssueNumberBody issueNumberBody) {
    SubProcessCallStartParamCaller caller =  createCallSubProcessWithStartPath(PATCH_ISSUE_START)
        .withParam(GitHubConstants.OWNER, owner)
        .withParam(GitHubConstants.REPO, repo)
        .withParam(GitHubParamConstants.ISSUE_NUMBER, issueNumber)
        .withParam(GitHubParamConstants.ISSUE_REQUEST, issueNumberBody);
    return caller.call().get(ISSUE_RESULT, Issue.class);
  }

  private void validateParams(String owner, String repo, BigInteger issueNumber) {
    ObjectUtils.requireNonEmpty(owner, "Owner must not be empty");
    ObjectUtils.requireNonEmpty(repo, "Repo must not be empty");
    ObjectUtils.requireNonEmpty(issueNumber, "IssueNumber must not be empty");
  }

  @Override
  protected String getProcessName() {
    return ISSUE_PROCESS;
  }
}
