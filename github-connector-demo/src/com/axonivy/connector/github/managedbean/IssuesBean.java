package com.axonivy.connector.github.managedbean;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.converter.JSONConverter;
import com.axonivy.connector.github.enums.IssueState;
import com.axonivy.connector.github.models.IssuesModel;
import com.axonivy.connector.github.models.criteria.SearchIssueCriteria;
import com.axonivy.connector.github.service.GitHubIssueService;
import com.axonivy.connector.github.util.GitHubApiUtils;
import com.axonivy.connector.github.util.VariableUtils;
import com.github.api.client.Issue;
import com.github.api.client.IssueComment;
import com.github.api.client.IssuesIssueNumberBody;
import com.github.api.client.SimpleUser;
import com.github.api.client.User;

import ch.ivyteam.ivy.process.call.SubProcessCall;

@ManagedBean
@ViewScoped
public class IssuesBean {
  private String keyword;
  private String orgName;
  private String repoName;
  private String assignee;
  private IssueState issueState;
  private boolean showNoAssignee;
  private boolean showNoComment;
  private List<LocalDate> createdRange;
  private List<LocalDate> updatedRange;
  private String comment;
  private Issue selectedIssue;
  private IssuesModel issuesModel;
  private List<SimpleUser> users;
  private List<Object> selectedUsernames;
  private IssuesIssueNumberBody updateIssue;
  private List<IssueComment> issueComments;
  private GitHubIssueService issueService;

  @PostConstruct
  public void init() {
    updateIssue = new IssuesIssueNumberBody();
    issueService = GitHubIssueService.getInstance();
    orgName = VariableUtils.getDefaultOrg();
    var criteria = SearchIssueCriteria.builder()
        .org(orgName)
        .stateOpen()
        .build();
    issuesModel = new IssuesModel(criteria);
  }

  public void searchIssues() {
    var criteria = SearchIssueCriteria.builder().keywords(keyword);
    if (IssueState.CLOSED == issueState) {
      criteria.stateClosed();
    }
    if (IssueState.OPEN == issueState) {
      criteria.stateOpen();
    }
    if (showNoComment) {
      criteria.comments(0);
    }
    if (showNoAssignee) {
      criteria.noAssignee();
    } else if (StringUtils.isNoneBlank(assignee)) {
      criteria.assignee(assignee);
    } else {
      criteria.assigneeAny();
    }
    if (StringUtils.isNoneBlank(repoName)) {
      criteria.repo(orgName, repoName);
    } else if (StringUtils.isNoneBlank(orgName)) {
      criteria.org(orgName);
    }

    if (createdRange != null) {
      criteria.createdBetween(createdRange.getFirst(), createdRange.getLast());
    }
    if (updatedRange != null) {
      criteria.updatedBetween(updatedRange.getFirst(), updatedRange.getLast());
    }
    issuesModel.search(criteria.build());
  }

  public void addCommentToIssue() {
    if (selectedIssue == null) {
      this.comment = null;
      return;
    }
    String repoUrl = selectedIssue.getRepositoryUrl().toString();
    var owner = GitHubApiUtils.extractRepoOwner(repoUrl);
    var repoName = GitHubApiUtils.extractRepoName(repoUrl);
    BigInteger issueNumber = new BigInteger(selectedIssue.getNumber().toString());
    FacesMessage message;
    try {
      IssueComment issueComment = issueService.addCommentToIssue(owner, repoName, issueNumber, comment);
      if (issueComment != null) {
        message = new FacesMessage("Added Comment",
            "Your comment is added to the issue " + issueNumber + " successfull with #Id:" + issueComment.getId());
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        this.comment = null;
      } else {
        message = new FacesMessage("Failed", "Cannot add your comment to the issue " + issueNumber);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
      }
    } catch (Exception e) {
      message = new FacesMessage("Failed", "Exception " + e.getMessage());
      message.setSeverity(FacesMessage.SEVERITY_ERROR);
      FacesContext.getCurrentInstance().validationFailed();
    }
    FacesContext.getCurrentInstance().addMessage(null, message);
  }

  public void assignUsersToIssue() {
    if (selectedIssue == null) {
      return;
    }
    String repoUrl = selectedIssue.getRepositoryUrl().toString();
    var owner = GitHubApiUtils.extractRepoOwner(repoUrl);
    var repoName = GitHubApiUtils.extractRepoName(repoUrl);
    BigInteger issueNumber = new BigInteger(selectedIssue.getNumber().toString());
    FacesMessage message;
    try {
      Issue issue = issueService.assignUsersToIssue(owner, repoName, issueNumber,
          selectedUsernames.stream().map(Object::toString).toList());
      if (issue != null) {
        message = new FacesMessage("Assign Users", "Assigned users to the issue " + issueNumber + " successfull");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        selectedUsernames.clear();
      } else {
        message = new FacesMessage("Failed", "Cannot assign users to the issue " + issueNumber);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
      }
    } catch (Exception e) {
      message = new FacesMessage("Failed", "Exception " + e.getMessage());
      message.setSeverity(FacesMessage.SEVERITY_ERROR);
      FacesContext.getCurrentInstance().validationFailed();
    }
    FacesContext.getCurrentInstance().addMessage(null, message);
  }

  public void patchIssue() {
    if (updateIssue == null) {
      return;
    }
    String repoUrl = selectedIssue.getRepositoryUrl().toString();
    var owner = GitHubApiUtils.extractRepoOwner(repoUrl);
    var repoName = GitHubApiUtils.extractRepoName(repoUrl);
    BigInteger issueNumber = new BigInteger(selectedIssue.getNumber().toString());
    FacesMessage message;
    try {
      Issue issue = issueService.patchIssue(owner, repoName, issueNumber, updateIssue);
      if (issue != null) {
        message = new FacesMessage("Patch Issue", "Patch the issue " + issueNumber + " successfull");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        selectedUsernames.clear();
      } else {
        message = new FacesMessage("Failed", "Cannot update the issue " + issueNumber);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
      }
    } catch (Exception e) {
      message = new FacesMessage("Failed", "Exception " + e.getMessage());
      message.setSeverity(FacesMessage.SEVERITY_ERROR);
      FacesContext.getCurrentInstance().validationFailed();
    }
    FacesContext.getCurrentInstance().addMessage(null, message);
  }

  public void onSelectIssue(Issue issue) {
    setSelectedIssue(issue);
    updateIssue = new IssuesIssueNumberBody();
    if (issue != null) {
      updateIssue.setTitle(issue.getTitle());
      updateIssue.setBody(issue.getBody());
      updateIssue.setAssignees(issue.getAssignees());
      updateIssue.setLabels(issue.getLabels());
      updateIssue.setMilestone(issue.getMilestone());
      updateIssue.setState(issue.getState());
      updateIssue.setStateReason(issue.getStateReason());
    }
  }

  public List<User> getAssigneesOfIssue() {
    if (selectedIssue == null) {
      return List.of();
    }
    return JSONConverter.convertToList(selectedIssue.getAssignees(), User.class);
  }

  public List<SimpleUser> onCompleteUserSearch(String filter) {
    if (CollectionUtils.isEmpty(users)) {
      loadUsersOfCurrentOrg();
    }
    return users.stream().filter(user -> isContainsKeyword(user.getLogin(), filter)
          || isContainsKeyword(user.getName(), filter)
          || isContainsKeyword(user.getEmail(), filter))
        .toList();
  }

  public String getAssignessDisplayName(Issue issue) {
    if (issue == null || issue.getAssignees() == null) {
      return StringUtils.EMPTY;
    }
    List<User> users = JSONConverter.convertToList(issue.getAssignees(), User.class);
    return users.stream().map(User::getLogin)
        .map(Object::toString)
        .collect(Collectors.joining("; "));
  }

  public String getDisplayUser(SimpleUser simpleUser) {
    if (simpleUser == null) {
      return "N/A";
    }
    final String displayUserPattern = "%s (%s)";
    return displayUserPattern.formatted(simpleUser.getLogin(), simpleUser.getEmail());
  }

  public void loadCommentsForIssue(Issue issue) {
    if (issue == null) {
      return;
    }
    String repoUrl = issue.getRepositoryUrl().toString();
    var owner = GitHubApiUtils.extractRepoOwner(repoUrl);
    var repoName = GitHubApiUtils.extractRepoName(repoUrl);
    BigInteger issueNumber = new BigInteger(issue.getNumber().toString());
    issueComments = issueService.getIssueComments(owner, repoName, issueNumber);
  }
  
  public List<IssueState> getIssueStates() {
    return List.of(IssueState.OPEN, IssueState.CLOSED);
  }

private void loadUsersOfCurrentOrg() {
    var result = SubProcessCall.withPath("GitHubOrg").withStartName("getOrgMembers")
        .withParam("org", orgName)
        .withParam("page", 1)
        .withParam("pageSize", 100)
        .call();
    users = JSONConverter.convertListObjectsToNewList(result.get("users"), SimpleUser.class);
  }

  private boolean isContainsKeyword(Object value, String filter) {
    if (value == null) {
      return false;
    }
    return value.toString().contains(filter);
  }
  public IssuesModel getIssuesModel() {
    return issuesModel;
  }

  public void setIssuesModel(IssuesModel issuesModel) {
    this.issuesModel = issuesModel;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getRepoName() {
    return repoName;
  }

  public void setRepoName(String repoName) {
    this.repoName = repoName;
  }


  public boolean isShowNoAssignee() {
    return showNoAssignee;
  }

  public void setShowNoAssignee(boolean showNoAssignee) {
    this.showNoAssignee = showNoAssignee;
  }

  public List<LocalDate> getCreatedRange() {
    return createdRange;
  }

  public void setCreatedRange(List<LocalDate> createdRange) {
    this.createdRange = createdRange;
  }

  public List<LocalDate> getUpdatedRange() {
    return updatedRange;
  }

  public void setUpdatedRange(List<LocalDate> updatedRange) {
    this.updatedRange = updatedRange;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Issue getSelectedIssue() {
    return selectedIssue;
  }

  public void setSelectedIssue(Issue selectedIssue) {
    this.selectedIssue = selectedIssue;
  }

  public List<SimpleUser> getUsers() {
    return users;
  }

  public void setUsers(List<SimpleUser> users) {
    this.users = users;
  }

  public List<Object> getSelectedUsernames() {
    return selectedUsernames;
  }

  public void setSelectedUsernames(List<Object> selectedUsernames) {
    this.selectedUsernames = selectedUsernames;
  }

  public IssuesIssueNumberBody getUpdateIssue() {
    return updateIssue;
  }

  public void setUpdateIssue(IssuesIssueNumberBody updateIssue) {
    this.updateIssue = updateIssue;
  }

  public IssueState getIssueState() {
    return issueState;
  }

  public void setIssueState(IssueState issueState) {
    this.issueState = issueState;
  }

  public boolean isShowNoComment() {
    return showNoComment;
  }

  public void setShowNoComment(boolean showNoComment) {
    this.showNoComment = showNoComment;
  }

  public List<IssueComment> getIssueComments() {
    return issueComments;
  }

  public void setIssueComments(List<IssueComment> issueComments) {
    this.issueComments = issueComments;
  }
}
