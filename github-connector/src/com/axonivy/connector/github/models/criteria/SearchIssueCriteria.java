package com.axonivy.connector.github.models.criteria;

import static com.axonivy.connector.github.constant.GitHubParamConstants.ASSIGNEE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.AUTHOR;
import static com.axonivy.connector.github.constant.GitHubParamConstants.BODY;
import static com.axonivy.connector.github.constant.GitHubParamConstants.CLOSED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.COMMENTER;
import static com.axonivy.connector.github.constant.GitHubParamConstants.COMMENTS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.COMPLETED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_NEGATION;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_PATH;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_GREATER;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_LESS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_QUOTED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_RANGE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_VALUE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_TEXT_IN;
import static com.axonivy.connector.github.constant.GitHubParamConstants.IN;
import static com.axonivy.connector.github.constant.GitHubParamConstants.INTERACTIONS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.INVOLVES;
import static com.axonivy.connector.github.constant.GitHubParamConstants.IS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.ISSUE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.LABEL;
import static com.axonivy.connector.github.constant.GitHubParamConstants.LINKED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.LOCKED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.MENTIONS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.MILESTONE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.NO;
import static com.axonivy.connector.github.constant.GitHubParamConstants.NOT_PLANNED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.PR;
import static com.axonivy.connector.github.constant.GitHubParamConstants.PRIVATE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.PROJECT;
import static com.axonivy.connector.github.constant.GitHubParamConstants.PUBLIC;
import static com.axonivy.connector.github.constant.GitHubParamConstants.REACTIONS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.REASON;
import static com.axonivy.connector.github.constant.GitHubParamConstants.STATE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.TITLE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.TYPE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.UNLOCKED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.UPDATED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.WILDCARD;
import static org.apache.commons.lang3.StringUtils.joinWith;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.enums.IssueState;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchIssueCriteria extends SearchCriteria {
  protected String inTitle;
  protected String inBody;
  protected String inComments;
  protected String assignee;
  protected Boolean assigneeAny;
  protected Boolean noAssignee;
  protected String author;
  protected String commenter;
  protected String involves;
  protected String mentions;
  protected List<String> labels;
  protected Boolean noLabel;
  protected String milestone;
  protected Boolean noMilestone;
  protected String project;
  protected Boolean noProject;
  protected String comments;
  protected String commentsMin;
  protected String commentsMax;
  protected String interactions;
  protected String interactionsMin;
  protected String interactionsMax;
  protected String reactions;
  protected String reactionsMin;
  protected String reactionsMax;
  protected LocalDate updatedAfter;
  protected LocalDate updatedBefore;
  protected LocalDate closedAfter;
  protected LocalDate closedBefore;
  protected String state;
  protected Boolean locked;
  protected Boolean linkedPr;
  protected String reason;

  public SearchIssueCriteria() {
    this.labels = new ArrayList<>();
  }

  @Override
  protected List<String> buildQueryFromSelfProperties() {
    List<String> parts = new ArrayList<>();
    if (StringUtils.isNotBlank(state)) {
      parts.add(joinWith(GitHubConstants.COLON, STATE, state));
    }
    // Assignee
    if (StringUtils.isNotBlank(assignee)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ASSIGNEE, assignee));
    }
    if (Boolean.TRUE.equals(assigneeAny)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ASSIGNEE, WILDCARD));
    }
    if (Boolean.TRUE.equals(noAssignee)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, ASSIGNEE));
    }
    // Users
    if (StringUtils.isNotBlank(author)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, AUTHOR, author));
    }
    if (StringUtils.isNotBlank(commenter)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, COMMENTER, commenter));
    }
    if (StringUtils.isNotBlank(involves)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, INVOLVES, involves));
    }
    if (StringUtils.isNotBlank(mentions)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, MENTIONS, mentions));
    }
    // Labels
    if (labels != null && !labels.isEmpty()) {
      for (String label : labels) {
        if (label.contains(StringUtils.SPACE)) {
          parts.add(String.format(FORMAT_QUALIFIER_QUOTED, LABEL, label));
        } else {
          parts.add(String.format(FORMAT_QUALIFIER_VALUE, LABEL, label));
        }
      }
    }
    if (Boolean.TRUE.equals(noLabel)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, LABEL));
    }
    // Milestone
    if (StringUtils.isNotBlank(milestone)) {
      if (milestone.contains(StringUtils.SPACE)) {
        parts.add(String.format(FORMAT_QUALIFIER_QUOTED, MILESTONE, milestone));
      } else {
        parts.add(String.format(FORMAT_QUALIFIER_VALUE, MILESTONE, milestone));
      }
    }
    if (Boolean.TRUE.equals(noMilestone)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, MILESTONE));
    }
    if (StringUtils.isNotBlank(project)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, PROJECT, project));
    }
    if (Boolean.TRUE.equals(noProject)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, PROJECT));
    }
    // Comments
    if (comments != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, COMMENTS, comments));
    } else if (commentsMin != null && commentsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, COMMENTS, commentsMin, commentsMax));
    } else if (commentsMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, COMMENTS, commentsMin));
    } else if (commentsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, COMMENTS, commentsMax));
    }
    // Interactions
    if (interactions != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, INTERACTIONS, interactions));
    } else if (interactionsMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, INTERACTIONS, interactionsMin));
    } else if (interactionsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, INTERACTIONS, interactionsMax));
    }
    // Reactions
    if (reactions != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REACTIONS, reactions));
    } else if (reactionsMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, REACTIONS, reactionsMin));
    } else if (reactionsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, REACTIONS, reactionsMax));
    }
    // Status
    if (Boolean.TRUE.equals(locked)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, LOCKED));
    } else if (Boolean.FALSE.equals(locked)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, UNLOCKED));
    }
    if (Boolean.TRUE.equals(isPublic)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, PUBLIC));
    }
    if (Boolean.TRUE.equals(isPrivate)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, PRIVATE));
    }
    // Search scope
    if (StringUtils.isNotBlank(inTitle)) {
      parts.add(String.format(FORMAT_TEXT_IN, inTitle, IN, TITLE));
    }
    if (StringUtils.isNotBlank(inBody)) {
      parts.add(String.format(FORMAT_TEXT_IN, inBody, IN, BODY));
    }
    if (StringUtils.isNotBlank(inComments)) {
      parts.add(String.format(FORMAT_TEXT_IN, inComments, IN, COMMENTS));
    }
    // Links
    if (Boolean.TRUE.equals(linkedPr)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, LINKED, PR));
    } else if (Boolean.FALSE.equals(linkedPr)) {
      parts.add(String.format(FORMAT_NEGATION, LINKED, PR));
    }
    // Reason
    if (StringUtils.isNotBlank(reason)) {
      if (reason.contains(StringUtils.SPACE)) {
        parts.add(String.format(FORMAT_QUALIFIER_QUOTED, REASON, reason));
      } else {
        parts.add(String.format(FORMAT_QUALIFIER_VALUE, REASON, reason));
      }
    }
    addDateRange(parts, UPDATED, updatedAfter, updatedBefore);
    addDateRange(parts, CLOSED, closedAfter, closedBefore);
    return parts;
  }

  @Override
  public String toString() {
    return getQuery();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder from(SearchIssueCriteria criteria) {
    Builder builder = new Builder();
    builder.criteria = criteria;
    return builder;
  }

  public static class Builder extends CriteriaBuilder<SearchIssueCriteria, Builder> {
    public Builder() {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, TYPE, ISSUE));
      criteria = new SearchIssueCriteria();
    }

    @Override
    protected Builder builderType() {
      return this;
    }

    /**
     * Search open issues
     */
    public Builder stateOpen() {
      criteria.state = IssueState.OPEN.getState();
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, STATE, IssueState.OPEN.getState()));
      return this;
    }

    /**
     * Search closed issues
     */
    public Builder stateClosed() {
      criteria.state = IssueState.CLOSED.getState();
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, STATE, IssueState.CLOSED.getState()));
      return this;
    }

    /**
     * Search by assignee
     */
    public Builder assignee(String username) {
      criteria.assignee = username;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ASSIGNEE, username));
      return this;
    }

    /**
     * Search for issues with any assignee
     */
    public Builder assigneeAny() {
      criteria.assigneeAny = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ASSIGNEE, WILDCARD));
      return this;
    }

    /**
     * Search for unassigned issues
     */
    public Builder noAssignee() {
      criteria.noAssignee = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, ASSIGNEE));
      return this;
    }

    /**
     * Search by issue author
     */
    public Builder author(String username) {
      criteria.author = username;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, AUTHOR, username));
      return this;
    }

    /**
     * Search by commenter
     */
    public Builder commenter(String username) {
      criteria.commenter = username;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, COMMENTER, username));
      return this;
    }

    /**
     * Search by anyone involved (author, assignee, commenter, mentioned)
     */
    public Builder involves(String username) {
      criteria.involves = username;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, INVOLVES, username));
      return this;
    }

    /**
     * Search by mentioned user
     */
    public Builder mentions(String username) {
      criteria.mentions = username;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, MENTIONS, username));
      return this;
    }

    /**
     * Search by label
     */
    public Builder label(String label) {
      criteria.labels.add(label);
      if (label.contains(StringUtils.SPACE)) {
        parts.add(String.format(FORMAT_QUALIFIER_QUOTED, LABEL, label));
      } else {
        parts.add(String.format(FORMAT_QUALIFIER_VALUE, LABEL, label));
      }
      return this;
    }

    /**
     * Search by multiple labels (AND condition)
     */
    public Builder labels(String... labels) {
      for (String label : labels) {
        label(label);
      }
      return this;
    }

    /**
     * Search for issues with no labels
     */
    public Builder noLabel() {
      criteria.noLabel = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, LABEL));
      return this;
    }

    /**
     * Search by milestone
     */
    public Builder milestone(String milestone) {
      criteria.milestone = milestone;
      if (milestone.contains(StringUtils.SPACE)) {
        parts.add(String.format(FORMAT_QUALIFIER_QUOTED, MILESTONE, milestone));
      } else {
        parts.add(String.format(FORMAT_QUALIFIER_VALUE, MILESTONE, milestone));
      }
      return this;
    }

    /**
     * Search for issues with no milestone
     */
    public Builder noMilestone() {
      criteria.noMilestone = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, MILESTONE));
      return this;
    }

    /**
     * Search by project board
     */
    public Builder project(String owner, String projectNumber) {
      String projectValue = String.format(FORMAT_PATH, owner, projectNumber);
      criteria.project = projectValue;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, PROJECT, projectValue));
      return this;
    }

    /**
     * Search for issues with no project
     */
    public Builder noProject() {
      criteria.noProject = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, PROJECT));
      return this;
    }

    /**
     * Search by number of comments
     */
    public Builder comments(int count) {
      criteria.comments = String.valueOf(count);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, COMMENTS, count));
      return this;
    }

    /**
     * Search by comment count range
     */
    public Builder commentsGreaterThan(int count) {
      criteria.commentsMin = String.valueOf(count);
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, COMMENTS, count));
      return this;
    }

    public Builder commentsLessThan(int count) {
      criteria.commentsMax = String.valueOf(count);
      parts.add(String.format(FORMAT_QUALIFIER_LESS, COMMENTS, count));
      return this;
    }

    public Builder commentsRange(int min, int max) {
      criteria.commentsMin = String.valueOf(min);
      criteria.commentsMax = String.valueOf(max);
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, COMMENTS, min, max));
      return this;
    }

    /**
     * Search locked issues
     */
    public Builder locked() {
      criteria.locked = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, LOCKED));
      return this;
    }

    /**
     * Search unlocked issues
     */
    public Builder unlocked() {
      criteria.locked = false;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, UNLOCKED));
      return this;
    }

    /**
     * Search in title
     */
    public Builder inTitle(String text) {
      criteria.inTitle = text;
      parts.add(String.format(FORMAT_TEXT_IN, text, IN, TITLE));
      return this;
    }

    /**
     * Search in body
     */
    public Builder inBody(String text) {
      criteria.inBody = text;
      parts.add(String.format(FORMAT_TEXT_IN, text, IN, BODY));
      return this;
    }

    /**
     * Search in comments
     */
    public Builder inComments(String text) {
      criteria.inComments = text;
      parts.add(String.format(FORMAT_TEXT_IN, text, IN, COMMENTS));
      return this;
    }

    /**
     * Search by number of interactions (comments + reactions)
     */
    public Builder interactions(int count) {
      criteria.interactions = String.valueOf(count);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, INTERACTIONS, count));
      return this;
    }

    public Builder interactionsGreaterThan(int count) {
      criteria.interactionsMin = String.valueOf(count);
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, INTERACTIONS, count));
      return this;
    }

    public Builder interactionsLessThan(int count) {
      criteria.interactionsMax = String.valueOf(count);
      parts.add(String.format(FORMAT_QUALIFIER_LESS, INTERACTIONS, count));
      return this;
    }

    /**
     * Search by number of reactions
     */
    public Builder reactions(int count) {
      criteria.reactions = String.valueOf(count);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REACTIONS, count));
      return this;
    }

    public Builder reactionsGreaterThan(int count) {
      criteria.reactionsMin = String.valueOf(count);
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, REACTIONS, count));
      return this;
    }

    public Builder reactionsLessThan(int count) {
      criteria.reactionsMax = String.valueOf(count);
      parts.add(String.format(FORMAT_QUALIFIER_LESS, REACTIONS, count));
      return this;
    }

    /**
     * Search issues linked to a PR
     */
    public Builder linkedPr() {
      criteria.linkedPr = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, LINKED, PR));
      return this;
    }

    /**
     * Search issues not linked to a PR
     */
    public Builder notLinkedPr() {
      criteria.linkedPr = false;
      parts.add(String.format(FORMAT_NEGATION, LINKED, PR));
      return this;
    }

    /**
     * Search by close reason
     */
    public Builder reason(String reason) {
      criteria.reason = reason;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REASON, reason));
      return this;
    }

    public Builder reasonCompleted() {
      criteria.reason = COMPLETED;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REASON, COMPLETED));
      return this;
    }

    public Builder reasonNotPlanned() {
      criteria.reason = NOT_PLANNED;
      parts.add(String.format(FORMAT_QUALIFIER_QUOTED, REASON, NOT_PLANNED));
      return this;
    }

    public Builder updatedAfter(LocalDate date) {
      criteria.updatedAfter = date;
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, UPDATED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder updatedBefore(LocalDate date) {
      criteria.updatedBefore = date;
      parts.add(String.format(FORMAT_QUALIFIER_LESS, UPDATED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder updatedBetween(LocalDate from, LocalDate to) {
      criteria.updatedAfter = from;
      criteria.updatedBefore = to;
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, UPDATED, from.format(DateTimeFormatter.ISO_DATE),
          to.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    /**
     * Search by closed date
     */
    public Builder closed(String date) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, CLOSED, date));
      return this;
    }

    public Builder closedAfter(LocalDate date) {
      criteria.closedAfter = date;
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, CLOSED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder closedBefore(LocalDate date) {
      criteria.closedBefore = date;
      parts.add(String.format(FORMAT_QUALIFIER_LESS, CLOSED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }
  }
}