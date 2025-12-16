package com.axonivy.connector.github.models.criteria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import static com.axonivy.connector.github.constant.GitHubConstants.*;
import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.enums.IssueState;

import static org.apache.commons.lang3.StringUtils.joinWith;

public class SearchIssueCriteria {
  public static final String SLASH = "/";
  public static final String COLON = ":";
  public static final String QUERY = "query";
  public static final String PAGE = "page";
  public static final String PAGE_SIZE = "pageSize";

  // Query qualifiers
  public static final String TYPE = "type";
  public static final String STATE = "state";
  public static final String IS = "is";
  public static final String ORG = "org";
  public static final String REPO = "repo";
  public static final String USER = "user";
  public static final String ASSIGNEE = "assignee";
  public static final String NO = "no";
  public static final String AUTHOR = "author";
  public static final String COMMENTER = "commenter";
  public static final String INVOLVES = "involves";
  public static final String MENTIONS = "mentions";
  public static final String LABEL = "label";
  public static final String MILESTONE = "milestone";
  public static final String PROJECT = "project";
  public static final String COMMENTS = "comments";
  public static final String CREATED = "created";
  public static final String UPDATED = "updated";
  public static final String CLOSED = "closed";
  public static final String MERGED = "merged";
  public static final String REVIEW = "review";
  public static final String REVIEWED_BY = "reviewed-by";
  public static final String REVIEW_REQUESTED = "review-requested";
  public static final String TEAM_REVIEW_REQUESTED = "team-review-requested";
  public static final String BASE = "base";
  public static final String HEAD = "head";
  public static final String IN = "in";
  public static final String INTERACTIONS = "interactions";
  public static final String REACTIONS = "reactions";
  public static final String LINKED = "linked";
  public static final String REASON = "reason";
  public static final String LANGUAGE = "language";
  public static final String ARCHIVED = "archived";
  public static final String SORT = "sort";
  
  // Values
  public static final String ISSUE = "issue";
  public static final String PR = "pr";
  public static final String DRAFT = "draft";
  public static final String LOCKED = "locked";
  public static final String UNLOCKED = "unlocked";
  public static final String PUBLIC = "public";
  public static final String PRIVATE = "private";
  public static final String TITLE = "title";
  public static final String BODY = "body";
  public static final String REQUIRED = "required";
  public static final String APPROVED = "approved";
  public static final String CHANGES_REQUESTED = "changes_requested";
  public static final String COMPLETED = "completed";
  public static final String NOT_PLANNED = "not planned";
  
  // Format patterns
  public static final String FORMAT_QUALIFIER_VALUE = "%s:%s";
  public static final String FORMAT_QUALIFIER_QUOTED = "%s:\"%s\"";
  public static final String FORMAT_QUALIFIER_PATH = "%s:%s/%s";
  public static final String FORMAT_QUALIFIER_NUMBER = "%s:%d";
  public static final String FORMAT_QUALIFIER_GREATER = "%s:>%s";
  public static final String FORMAT_QUALIFIER_LESS = "%s:<%s";
  public static final String FORMAT_QUALIFIER_RANGE = "%s:%s..%s";
  public static final String FORMAT_QUALIFIER_GREATER_NUM = "%s:>%d";
  public static final String FORMAT_QUALIFIER_LESS_NUM = "%s:<%d";
  public static final String FORMAT_QUALIFIER_RANGE_NUM = "%s:%d..%d";
  public static final String FORMAT_TEXT_IN = "\"%s\" %s:%s";
  public static final String FORMAT_NEGATION = "-%s:%s";
  public static final String FORMAT_PATH = "%s/%s";
  public static final String WILDCARD = "*";
  private String keywords;
  private String org;
  private String repo;
  private String user;
  private String assignee;
  private Boolean assigneeAny;
  private Boolean noAssignee;
  private String author;
  private String commenter;
  private String involves;
  private String mentions;
  private List<String> labels;
  private Boolean noLabel;
  private String milestone;
  private Boolean noMilestone;
  private String project;
  private Boolean noProject;
  private Integer comments;
  private Integer commentsMin;
  private Integer commentsMax;
  private Integer interactions;
  private Integer interactionsMin;
  private Integer interactionsMax;
  private Integer reactions;
  private Integer reactionsMin;
  private Integer reactionsMax;
  private LocalDate createdAfter;
  private LocalDate createdBefore;
  private LocalDate updatedAfter;
  private LocalDate updatedBefore;
  private LocalDate closedAfter;
  private LocalDate closedBefore;
  private String state;
  private Boolean locked;
  private Boolean isPublic;
  private Boolean isPrivate;
  private String inTitle;
  private String inBody;
  private String inComments;
  private Boolean linkedPr;
  private String reason;
  private String language;
  private Boolean archived;
  private String rawQuery;
  
  // Generated query (transient - not for JSON)
  private transient String query;
  private String sort;

  // Constructors
  public SearchIssueCriteria() {
    this.labels = new ArrayList<>();
  }

  private SearchIssueCriteria(String query, String sort) {
    this();
    this.query = query;
    this.sort = sort;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
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

  public Integer getComments() {
    return comments;
  }

  public void setComments(Integer comments) {
    this.comments = comments;
  }

  public Integer getCommentsMin() {
    return commentsMin;
  }

  public void setCommentsMin(Integer commentsMin) {
    this.commentsMin = commentsMin;
  }

  public Integer getCommentsMax() {
    return commentsMax;
  }

  public void setCommentsMax(Integer commentsMax) {
    this.commentsMax = commentsMax;
  }

  public Integer getInteractions() {
    return interactions;
  }

  public void setInteractions(Integer interactions) {
    this.interactions = interactions;
  }

  public Integer getInteractionsMin() {
    return interactionsMin;
  }

  public void setInteractionsMin(Integer interactionsMin) {
    this.interactionsMin = interactionsMin;
  }

  public Integer getInteractionsMax() {
    return interactionsMax;
  }

  public void setInteractionsMax(Integer interactionsMax) {
    this.interactionsMax = interactionsMax;
  }

  public Integer getReactions() {
    return reactions;
  }

  public void setReactions(Integer reactions) {
    this.reactions = reactions;
  }

  public Integer getReactionsMin() {
    return reactionsMin;
  }

  public void setReactionsMin(Integer reactionsMin) {
    this.reactionsMin = reactionsMin;
  }

  public Integer getReactionsMax() {
    return reactionsMax;
  }

  public void setReactionsMax(Integer reactionsMax) {
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
      parts.add(String.format(FORMAT_QUALIFIER_NUMBER, COMMENTS, comments));
    } else if (commentsMin != null && commentsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_RANGE_NUM, COMMENTS, commentsMin, commentsMax));
    } else if (commentsMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER_NUM, COMMENTS, commentsMin));
    } else if (commentsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS_NUM, COMMENTS, commentsMax));
    }
    // Interactions
    if (interactions != null) {
      parts.add(String.format(FORMAT_QUALIFIER_NUMBER, INTERACTIONS, interactions));
    } else if (interactionsMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER_NUM, INTERACTIONS, interactionsMin));
    } else if (interactionsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS_NUM, INTERACTIONS, interactionsMax));
    }

    // Reactions
    if (reactions != null) {
      parts.add(String.format(FORMAT_QUALIFIER_NUMBER, REACTIONS, reactions));
    } else if (reactionsMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER_NUM, REACTIONS, reactionsMin));
    } else if (reactionsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS_NUM, REACTIONS, reactionsMax));
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
        .filter(s -> s != null && !s.isEmpty())
        .collect(Collectors.joining(StringUtils.SPACE));
  }

  private void addDateRange(List<String> parts, String field, LocalDate after, LocalDate before) {
    if (after != null && before != null) {
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, field,
          after.format(DateTimeFormatter.ISO_DATE),
          before.format(DateTimeFormatter.ISO_DATE)));
    } else if (after != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, field, after.format(DateTimeFormatter.ISO_DATE)));
    } else if (before != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, field, before.format(DateTimeFormatter.ISO_DATE)));
    }
  }

  @Override
  public String toString() {
    return getQuery();
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * Create a Builder from existing criteria (useful for modification)
   */
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
      criteria.setKeywords(keywords);
      return this;
    }

    /**
     * Search in specific organization
     * 
     * @param org Organization name (e.g., "axonivy-market")
     */
    public Builder org(String org) {
      criteria.setOrg(org);
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
      criteria.setRepo(fullName);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REPO, fullName));
      return this;
    }

    /**
     * Search in specific repository (full name)
     * 
     * @param fullName Repository full name (e.g., "owner/repo")
     */
    public Builder repo(String fullName) {
      criteria.setRepo(fullName);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REPO, fullName));
      return this;
    }

    /**
     * Search in user's repositories
     */
    public Builder user(String username) {
      criteria.setUser(username);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, USER, username));
      return this;
    }

    /**
     * Search open issues
     */
    public Builder stateOpen() {
      criteria.setState(IssueState.OPEN.getState());
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, STATE, IssueState.OPEN.getState()));
      return this;
    }

    /**
     * Search closed issues
     */
    public Builder stateClosed() {
      criteria.setState(IssueState.CLOSED.getState());
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, STATE, IssueState.CLOSED.getState()));
      return this;
    }

    /**
     * Search by assignee
     */
    public Builder assignee(String username) {
      criteria.setAssignee(username);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ASSIGNEE, username));
      return this;
    }

    /**
     * Search for issues with any assignee
     */
    public Builder assigneeAny() {
      criteria.setAssigneeAny(true);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ASSIGNEE, WILDCARD));
      return this;
    }

    /**
     * Search for unassigned issues
     */
    public Builder noAssignee() {
      criteria.setNoAssignee(true);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, ASSIGNEE));
      return this;
    }

    /**
     * Search by issue author
     */
    public Builder author(String username) {
      criteria.setAuthor(username);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, AUTHOR, username));
      return this;
    }

    /**
     * Search by commenter
     */
    public Builder commenter(String username) {
      criteria.setCommenter(username);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, COMMENTER, username));
      return this;
    }

    /**
     * Search by anyone involved (author, assignee, commenter, mentioned)
     */
    public Builder involves(String username) {
      criteria.setInvolves(username);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, INVOLVES, username));
      return this;
    }

    /**
     * Search by mentioned user
     */
    public Builder mentions(String username) {
      criteria.setMentions(username);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, MENTIONS, username));
      return this;
    }

    /**
     * Search by label
     */
    public Builder label(String label) {
      criteria.getLabels().add(label);
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
      criteria.setNoLabel(true);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, LABEL));
      return this;
    }

    /**
     * Search by milestone
     */
    public Builder milestone(String milestone) {
      criteria.setMilestone(milestone);
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
      criteria.setNoMilestone(true);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, MILESTONE));
      return this;
    }

    /**
     * Search by project board
     */
    public Builder project(String owner, String projectNumber) {
      String projectValue = String.format(FORMAT_PATH, owner, projectNumber);
      criteria.setProject(projectValue);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, PROJECT, projectValue));
      return this;
    }

    /**
     * Search for issues with no project
     */
    public Builder noProject() {
      criteria.setNoProject(true);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, NO, PROJECT));
      return this;
    }

    /**
     * Search by number of comments
     */
    public Builder comments(int count) {
      criteria.setComments(count);
      parts.add(String.format(FORMAT_QUALIFIER_NUMBER, COMMENTS, count));
      return this;
    }

    /**
     * Search by comment count range
     */
    public Builder commentsGreaterThan(int count) {
      criteria.setCommentsMin(count);
      parts.add(String.format(FORMAT_QUALIFIER_GREATER_NUM, COMMENTS, count));
      return this;
    }

    public Builder commentsLessThan(int count) {
      criteria.setCommentsMax(count);
      parts.add(String.format(FORMAT_QUALIFIER_LESS_NUM, COMMENTS, count));
      return this;
    }

    public Builder commentsRange(int min, int max) {
      criteria.setCommentsMin(min);
      criteria.setCommentsMax(max);
      parts.add(String.format(FORMAT_QUALIFIER_RANGE_NUM, COMMENTS, min, max));
      return this;
    }

    /**
     * Search by creation date
     */
    public Builder created(String date) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, CREATED, date));
      return this;
    }

    public Builder createdAfter(LocalDate date) {
      criteria.setCreatedAfter(date);
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, CREATED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder createdBefore(LocalDate date) {
      criteria.setCreatedBefore(date);
      parts.add(String.format(FORMAT_QUALIFIER_LESS, CREATED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder createdBetween(LocalDate from, LocalDate to) {
      criteria.setCreatedAfter(from);
      criteria.setCreatedBefore(to);
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
      criteria.setUpdatedAfter(date);
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, UPDATED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder updatedBefore(LocalDate date) {
      criteria.setUpdatedBefore(date);
      parts.add(String.format(FORMAT_QUALIFIER_LESS, UPDATED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder updatedBetween(LocalDate from, LocalDate to) {
      criteria.setUpdatedAfter(from);
      criteria.setUpdatedBefore(to);
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
      criteria.setClosedAfter(date);
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, CLOSED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    public Builder closedBefore(LocalDate date) {
      criteria.setClosedBefore(date);
      parts.add(String.format(FORMAT_QUALIFIER_LESS, CLOSED, date.format(DateTimeFormatter.ISO_DATE)));
      return this;
    }

    /**
     * Search locked issues
     */
    public Builder locked() {
      criteria.setLocked(true);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, LOCKED));
      return this;
    }

    /**
     * Search unlocked issues
     */
    public Builder unlocked() {
      criteria.setLocked(false);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, UNLOCKED));
      return this;
    }

    /**
     * Search public issues
     */
    public Builder isPublic() {
      criteria.setIsPublic(true);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, PUBLIC));
      return this;
    }

    /**
     * Search private issues
     */
    public Builder isPrivate() {
      criteria.setIsPrivate(true);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, PRIVATE));
      return this;
    }

    /**
     * Search in title
     */
    public Builder inTitle(String text) {
      criteria.setInTitle(text);
      parts.add(String.format(FORMAT_TEXT_IN, text, IN, TITLE));
      return this;
    }

    /**
     * Search in body
     */
    public Builder inBody(String text) {
      criteria.setInBody(text);
      parts.add(String.format(FORMAT_TEXT_IN, text, IN, BODY));
      return this;
    }

    /**
     * Search in comments
     */
    public Builder inComments(String text) {
      criteria.setInComments(text);
      parts.add(String.format(FORMAT_TEXT_IN, text, IN, COMMENTS));
      return this;
    }

    /**
     * Search by number of interactions (comments + reactions)
     */
    public Builder interactions(int count) {
      criteria.setInteractions(count);
      parts.add(String.format(FORMAT_QUALIFIER_NUMBER, INTERACTIONS, count));
      return this;
    }

    public Builder interactionsGreaterThan(int count) {
      criteria.setInteractionsMin(count);
      parts.add(String.format(FORMAT_QUALIFIER_GREATER_NUM, INTERACTIONS, count));
      return this;
    }

    public Builder interactionsLessThan(int count) {
      criteria.setInteractionsMax(count);
      parts.add(String.format(FORMAT_QUALIFIER_LESS_NUM, INTERACTIONS, count));
      return this;
    }

    /**
     * Search by number of reactions
     */
    public Builder reactions(int count) {
      criteria.setReactions(count);
      parts.add(String.format(FORMAT_QUALIFIER_NUMBER, REACTIONS, count));
      return this;
    }

    public Builder reactionsGreaterThan(int count) {
      criteria.setReactionsMin(count);
      parts.add(String.format(FORMAT_QUALIFIER_GREATER_NUM, REACTIONS, count));
      return this;
    }

    public Builder reactionsLessThan(int count) {
      criteria.setReactionsMax(count);
      parts.add(String.format(FORMAT_QUALIFIER_LESS_NUM, REACTIONS, count));
      return this;
    }

    /**
     * Search issues linked to a PR
     */
    public Builder linkedPr() {
      criteria.setLinkedPr(true);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, LINKED, PR));
      return this;
    }

    /**
     * Search issues not linked to a PR
     */
    public Builder notLinkedPr() {
      criteria.setLinkedPr(false);
      parts.add(String.format(FORMAT_NEGATION, LINKED, PR));
      return this;
    }

    /**
     * Search by close reason
     */
    public Builder reason(String reason) {
      criteria.setReason(reason);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REASON, reason));
      return this;
    }

    public Builder reasonCompleted() {
      criteria.setReason(COMPLETED);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REASON, COMPLETED));
      return this;
    }

    public Builder reasonNotPlanned() {
      criteria.setReason(NOT_PLANNED);
      parts.add(String.format(FORMAT_QUALIFIER_QUOTED, REASON, NOT_PLANNED));
      return this;
    }

    /**
     * Search by repository language
     */
    public Builder language(String language) {
      criteria.setLanguage(language);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, LANGUAGE, language));
      return this;
    }

    /**
     * Include archived repositories
     */
    public Builder archived(boolean include) {
      criteria.setArchived(include);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ARCHIVED, String.valueOf(include)));
      return this;
    }

    /**
     * Add raw query string part
     */
    public Builder raw(String queryPart) {
      criteria.setRawQuery(queryPart);
      parts.add(queryPart);
      return this;
    }

    public SearchIssueCriteria build() {
      List<String> allParts = new ArrayList<>();
      if (keywords != null && !keywords.isEmpty()) {
        allParts.add(keywords);
      }
      // Always add type:issue
      allParts.add(String.format(FORMAT_QUALIFIER_VALUE, TYPE, ISSUE));
      allParts.addAll(parts);
      String query = allParts.stream().filter(s -> s != null && !s.isEmpty())
          .collect(Collectors.joining(StringUtils.SPACE));

      criteria.query = query;
      return criteria;
    }

    public String buildString() {
      return build().getQuery();
    }
  }
}