package com.axonivy.connector.github.models.criteria;

import static com.axonivy.connector.github.constant.GitHubConstants.ARCHIVED;
import static com.axonivy.connector.github.constant.GitHubConstants.ASSIGNEE;
import static com.axonivy.connector.github.constant.GitHubConstants.AUTHOR;
import static com.axonivy.connector.github.constant.GitHubConstants.BODY;
import static com.axonivy.connector.github.constant.GitHubConstants.CLOSED;
import static com.axonivy.connector.github.constant.GitHubConstants.COLON;
import static com.axonivy.connector.github.constant.GitHubConstants.COMMENTER;
import static com.axonivy.connector.github.constant.GitHubConstants.COMMENTS;
import static com.axonivy.connector.github.constant.GitHubConstants.COMPLETED;
import static com.axonivy.connector.github.constant.GitHubConstants.CREATED;
import static com.axonivy.connector.github.constant.GitHubConstants.FORMAT_NEGATION;
import static com.axonivy.connector.github.constant.GitHubConstants.FORMAT_PATH;
import static com.axonivy.connector.github.constant.GitHubConstants.FORMAT_QUALIFIER_GREATER;
import static com.axonivy.connector.github.constant.GitHubConstants.FORMAT_QUALIFIER_LESS;
import static com.axonivy.connector.github.constant.GitHubConstants.FORMAT_QUALIFIER_QUOTED;
import static com.axonivy.connector.github.constant.GitHubConstants.FORMAT_QUALIFIER_RANGE;
import static com.axonivy.connector.github.constant.GitHubConstants.FORMAT_QUALIFIER_VALUE;
import static com.axonivy.connector.github.constant.GitHubConstants.FORMAT_TEXT_IN;
import static com.axonivy.connector.github.constant.GitHubConstants.IN;
import static com.axonivy.connector.github.constant.GitHubConstants.INTERACTIONS;
import static com.axonivy.connector.github.constant.GitHubConstants.INVOLVES;
import static com.axonivy.connector.github.constant.GitHubConstants.IS;
import static com.axonivy.connector.github.constant.GitHubConstants.ISSUE;
import static com.axonivy.connector.github.constant.GitHubConstants.LABEL;
import static com.axonivy.connector.github.constant.GitHubConstants.LANGUAGE;
import static com.axonivy.connector.github.constant.GitHubConstants.LINKED;
import static com.axonivy.connector.github.constant.GitHubConstants.LOCKED;
import static com.axonivy.connector.github.constant.GitHubConstants.MENTIONS;
import static com.axonivy.connector.github.constant.GitHubConstants.MILESTONE;
import static com.axonivy.connector.github.constant.GitHubConstants.NO;
import static com.axonivy.connector.github.constant.GitHubConstants.NOT_PLANNED;
import static com.axonivy.connector.github.constant.GitHubConstants.ORG;
import static com.axonivy.connector.github.constant.GitHubConstants.PR;
import static com.axonivy.connector.github.constant.GitHubConstants.PRIVATE;
import static com.axonivy.connector.github.constant.GitHubConstants.PROJECT;
import static com.axonivy.connector.github.constant.GitHubConstants.PUBLIC;
import static com.axonivy.connector.github.constant.GitHubConstants.REACTIONS;
import static com.axonivy.connector.github.constant.GitHubConstants.REASON;
import static com.axonivy.connector.github.constant.GitHubConstants.REPO;
import static com.axonivy.connector.github.constant.GitHubConstants.STATE;
import static com.axonivy.connector.github.constant.GitHubConstants.TITLE;
import static com.axonivy.connector.github.constant.GitHubConstants.TYPE;
import static com.axonivy.connector.github.constant.GitHubConstants.UNLOCKED;
import static com.axonivy.connector.github.constant.GitHubConstants.UPDATED;
import static com.axonivy.connector.github.constant.GitHubConstants.USER;
import static com.axonivy.connector.github.constant.GitHubConstants.WILDCARD;
import static org.apache.commons.lang3.StringUtils.joinWith;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.enums.IssueState;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchIssueCriteria {
  protected String org;
  protected String repo;
  protected String keywords;
  protected String inTitle;
  protected String inBody;
  protected String inComments;
  protected String user;
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
  protected LocalDate createdAfter;
  protected LocalDate createdBefore;
  protected LocalDate updatedAfter;
  protected LocalDate updatedBefore;
  protected LocalDate closedAfter;
  protected LocalDate closedBefore;
  protected String state;
  protected Boolean locked;
  protected Boolean isPublic;
  protected Boolean isPrivate;
  protected Boolean linkedPr;
  protected String reason;
  protected String language;
  protected Boolean archived;
  protected String rawQuery;

  @JsonIgnore
  private transient String query;
  private String sort;

  public SearchIssueCriteria() {
    this.labels = new ArrayList<>();
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getQuery() {
    if (query != null) {
      return query;
    }
    return buildQueryFromProperties();
  }

  private String buildQueryFromProperties() {
    List<String> parts = new ArrayList<>();
    if (StringUtils.isNotBlank(keywords)) {
      parts.add(keywords);
    }
    parts.add(joinWith(COLON, TYPE, ISSUE));
    if (StringUtils.isNotBlank(state)) {
      parts.add(joinWith(GitHubConstants.COLON, STATE, state));
    }
    if (StringUtils.isNotBlank(org)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ORG, org));
    }
    if (StringUtils.isNotBlank(repo)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REPO, repo));
    }
    if (StringUtils.isNotBlank(user)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, USER, user));
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
    // Dates
    addDateRange(parts, CREATED, createdAfter, createdBefore);
    addDateRange(parts, UPDATED, updatedAfter, updatedBefore);
    addDateRange(parts, CLOSED, closedAfter, closedBefore);
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
    // Repository
    if (StringUtils.isNotBlank(language)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, LANGUAGE, language));
    }
    if (archived != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ARCHIVED, String.valueOf(archived)));
    }
    // Raw query
    if (StringUtils.isNotBlank(rawQuery)) {
      parts.add(rawQuery);
    }
    return parts.stream()
        .filter(StringUtils::isNoneBlank)
        .collect(Collectors.joining(StringUtils.SPACE));
  }

  private void addDateRange(List<String> parts, String field, LocalDate after, LocalDate before) {
    if (after != null && before != null) {
      var dateRange = String.format(FORMAT_QUALIFIER_RANGE, field, getDateAsIsoDate(after), getDateAsIsoDate(before));
      parts.add(dateRange);
    } else if (after != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, field, getDateAsIsoDate(after)));
    } else if (before != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, field, getDateAsIsoDate(before)));
    }
  }

  private String getDateAsIsoDate(LocalDate localDate) {
    if (localDate == null) {
      return StringUtils.EMPTY;
    }
    return localDate.format(DateTimeFormatter.ISO_DATE);
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

  public static class Builder {
    private final List<String> parts = new ArrayList<>();
    private SearchIssueCriteria criteria;
    private String keywords;

    public Builder() {
      this.criteria = new SearchIssueCriteria();
    }

    /**
     * Add free-text keywords to search
     */
    public Builder keywords(String keywords) {
      this.keywords = keywords;
      criteria.keywords = keywords;
      return this;
    }

    /**
     * Search in specific organization
     * 
     * @param org Organization name (e.g., "axonivy-market")
     */
    public Builder org(String org) {
      criteria.org = org;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ORG, org));
      return this;
    }

    /**
     * Search in specific repository
     * 
     * @param owner Repository owner
     * @param repo  Repository name
     */
    public Builder repo(String owner, String repo) {
      String fullName = String.format(FORMAT_PATH, owner, repo);
      criteria.repo = fullName;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REPO, fullName));
      return this;
    }

    /**
     * Search in user's repositories
     */
    public Builder user(String username) {
      criteria.user = username;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, USER, username));
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
     * Search by creation date
     */
    public Builder created(LocalDate date) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, CREATED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder createdAfter(LocalDate date) {
      criteria.createdAfter = date;
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, CREATED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder createdBefore(LocalDate date) {
      criteria.createdBefore = date;
      parts.add(String.format(FORMAT_QUALIFIER_LESS, CREATED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder createdBetween(LocalDate from, LocalDate to) {
      criteria.createdAfter = from;
      criteria.createdBefore = to;
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, CREATED, from.format(DateTimeFormatter.ISO_DATE),
          to.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    /**
     * Search by updated date
     */
    public Builder updated(String date) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, UPDATED, date));
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
     * Search public issues
     */
    public Builder isPublic() {
      criteria.isPublic = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, PUBLIC));
      return this;
    }

    /**
     * Search private issues
     */
    public Builder isPrivate() {
      criteria.isPrivate = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, PRIVATE));
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

    /**
     * Search by repository language
     */
    public Builder language(String language) {
      criteria.language = language;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, LANGUAGE, language));
      return this;
    }

    /**
     * Include archived repositories
     */
    public Builder archived(boolean archived) {
      criteria.archived = archived;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ARCHIVED, String.valueOf(archived)));
      return this;
    }

    /**
     * Add raw query string part
     */
    public Builder raw(String queryPart) {
      criteria.rawQuery = queryPart;
      parts.add(queryPart);
      return this;
    }

    public SearchIssueCriteria build() {
      List<String> allParts = new ArrayList<>();
      if (keywords != null && !keywords.isEmpty()) {
        allParts.add(keywords);
      }
      allParts.add(String.format(FORMAT_QUALIFIER_VALUE, TYPE, ISSUE));
      allParts.addAll(parts);
      String query = allParts.stream().filter(StringUtils::isNoneBlank)
          .collect(Collectors.joining(StringUtils.SPACE));

      criteria.query = query;
      return criteria;
    }

    public String buildString() {
      return build().getQuery();
    }
  }
}