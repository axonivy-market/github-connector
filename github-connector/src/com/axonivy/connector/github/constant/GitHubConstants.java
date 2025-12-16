package com.axonivy.connector.github.constant;

public class GitHubConstants {
  public GitHubConstants() {}
  public static final String SLASH = "/";
  public static final String COLON = ":";
  public static final String QUERY = "query";
  public static final String PAGE = "page";
  public static final String PAGE_SIZE = "pageSize";
  public static final String OWNER = "owner";

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
  public static final String FORMAT_QUALIFIER_GREATER = "%s:>%s";
  public static final String FORMAT_QUALIFIER_LESS = "%s:<%s";
  public static final String FORMAT_QUALIFIER_RANGE = "%s:%s..%s";
  public static final String FORMAT_TEXT_IN = "\"%s\" %s:%s";
  public static final String FORMAT_NEGATION = "-%s:%s";
  public static final String FORMAT_PATH = "%s/%s";
  public static final String WILDCARD = "*";
}
