package com.axonivy.connector.github.models;

import java.time.LocalDate;
import java.util.List;

import com.axonivy.connector.github.models.criteria.SearchIssueCriteria;

public class IssueScannerConfig extends SearchIssueCriteria {

  public IssueScannerConfig() {
    super();
  }

  public String getOrg() {
    return org;
  }

  public void setOrg(String org) {
    this.org = org;
  }

  public String getRepo() {
    return repo;
  }

  public void setRepo(String repo) {
    this.repo = repo;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getInTitle() {
    return inTitle;
  }

  public void setInTitle(String inTitle) {
    this.inTitle = inTitle;
  }

  public String getInBody() {
    return inBody;
  }

  public void setInBody(String inBody) {
    this.inBody = inBody;
  }

  public String getInComments() {
    return inComments;
  }

  public void setInComments(String inComments) {
    this.inComments = inComments;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public Boolean getAssigneeAny() {
    return assigneeAny;
  }

  public void setAssigneeAny(Boolean assigneeAny) {
    this.assigneeAny = assigneeAny;
  }

  public Boolean getNoAssignee() {
    return noAssignee;
  }

  public void setNoAssignee(Boolean noAssignee) {
    this.noAssignee = noAssignee;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getCommenter() {
    return commenter;
  }

  public void setCommenter(String commenter) {
    this.commenter = commenter;
  }

  public String getInvolves() {
    return involves;
  }

  public void setInvolves(String involves) {
    this.involves = involves;
  }

  public String getMentions() {
    return mentions;
  }

  public void setMentions(String mentions) {
    this.mentions = mentions;
  }

  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public Boolean getNoLabel() {
    return noLabel;
  }

  public void setNoLabel(Boolean noLabel) {
    this.noLabel = noLabel;
  }

  public String getMilestone() {
    return milestone;
  }

  public void setMilestone(String milestone) {
    this.milestone = milestone;
  }

  public Boolean getNoMilestone() {
    return noMilestone;
  }

  public void setNoMilestone(Boolean noMilestone) {
    this.noMilestone = noMilestone;
  }

  public String getProject() {
    return project;
  }

  public void setProject(String project) {
    this.project = project;
  }

  public Boolean getNoProject() {
    return noProject;
  }

  public void setNoProject(Boolean noProject) {
    this.noProject = noProject;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public String getCommentsMin() {
    return commentsMin;
  }

  public void setCommentsMin(String commentsMin) {
    this.commentsMin = commentsMin;
  }

  public String getCommentsMax() {
    return commentsMax;
  }

  public void setCommentsMax(String commentsMax) {
    this.commentsMax = commentsMax;
  }

  public String getInteractions() {
    return interactions;
  }

  public void setInteractions(String interactions) {
    this.interactions = interactions;
  }

  public String getInteractionsMin() {
    return interactionsMin;
  }

  public void setInteractionsMin(String interactionsMin) {
    this.interactionsMin = interactionsMin;
  }

  public String getInteractionsMax() {
    return interactionsMax;
  }

  public void setInteractionsMax(String interactionsMax) {
    this.interactionsMax = interactionsMax;
  }

  public String getReactions() {
    return reactions;
  }

  public void setReactions(String reactions) {
    this.reactions = reactions;
  }

  public String getReactionsMin() {
    return reactionsMin;
  }

  public void setReactionsMin(String reactionsMin) {
    this.reactionsMin = reactionsMin;
  }

  public String getReactionsMax() {
    return reactionsMax;
  }

  public void setReactionsMax(String reactionsMax) {
    this.reactionsMax = reactionsMax;
  }

  public LocalDate getCreatedAfter() {
    return createdAfter;
  }

  public void setCreatedAfter(LocalDate createdAfter) {
    this.createdAfter = createdAfter;
  }

  public LocalDate getCreatedBefore() {
    return createdBefore;
  }

  public void setCreatedBefore(LocalDate createdBefore) {
    this.createdBefore = createdBefore;
  }

  public LocalDate getUpdatedAfter() {
    return updatedAfter;
  }

  public void setUpdatedAfter(LocalDate updatedAfter) {
    this.updatedAfter = updatedAfter;
  }

  public LocalDate getUpdatedBefore() {
    return updatedBefore;
  }

  public void setUpdatedBefore(LocalDate updatedBefore) {
    this.updatedBefore = updatedBefore;
  }

  public LocalDate getClosedAfter() {
    return closedAfter;
  }

  public void setClosedAfter(LocalDate closedAfter) {
    this.closedAfter = closedAfter;
  }

  public LocalDate getClosedBefore() {
    return closedBefore;
  }

  public void setClosedBefore(LocalDate closedBefore) {
    this.closedBefore = closedBefore;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Boolean getLocked() {
    return locked;
  }

  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  public Boolean getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(Boolean isPublic) {
    this.isPublic = isPublic;
  }

  public Boolean getIsPrivate() {
    return isPrivate;
  }

  public void setIsPrivate(Boolean isPrivate) {
    this.isPrivate = isPrivate;
  }

  public Boolean getLinkedPr() {
    return linkedPr;
  }

  public void setLinkedPr(Boolean linkedPr) {
    this.linkedPr = linkedPr;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Boolean getArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  public String getRawQuery() {
    return rawQuery;
  }

  public void setRawQuery(String rawQuery) {
    this.rawQuery = rawQuery;
  }
}
