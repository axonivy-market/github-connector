package com.axonivy.connector.github.models.criteria;

import static com.axonivy.connector.github.constant.GitHubParamConstants.ARCHIVED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.CREATED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_PATH;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_GREATER;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_LESS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_RANGE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_VALUE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.IS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.LANGUAGE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.ORG;
import static com.axonivy.connector.github.constant.GitHubParamConstants.PRIVATE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.PUBLIC;
import static com.axonivy.connector.github.constant.GitHubParamConstants.REPO;
import static com.axonivy.connector.github.constant.GitHubParamConstants.UPDATED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.USER;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class SearchCriteria {
  protected String org;
  protected String repo;
  protected String keywords;
  protected String user;
  protected LocalDate createdAfter;
  protected LocalDate createdBefore;
  protected Boolean isPublic;
  protected Boolean isPrivate;
  protected String language;
  protected Boolean archived;
  protected String rawQuery;

  @JsonIgnore
  protected transient String query;
  protected String sort;

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
    List<String> parts = buildQueryFromProperties();
    return buildQueryByParts(parts);
  }

  protected abstract List<String> buildQueryFromProperties();
  
  protected String buildQueryByParts(List<String> parts) {
    if (StringUtils.isNotBlank(keywords)) {
      parts.add(keywords);
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
    addDateRange(parts, CREATED, createdAfter, createdBefore);

    if (Boolean.TRUE.equals(isPublic)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, PUBLIC));
    }
    if (Boolean.TRUE.equals(isPrivate)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, PRIVATE));
    }
    if (StringUtils.isNotBlank(language)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, LANGUAGE, language));
    }
    if (archived != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ARCHIVED, String.valueOf(archived)));
    }
    if (StringUtils.isNotBlank(rawQuery)) {
      parts.add(rawQuery);
    }
    return parts.stream()
        .filter(StringUtils::isNoneBlank)
        .collect(Collectors.joining(StringUtils.SPACE));
  }

  protected void addDateRange(List<String> parts, String field, LocalDate after, LocalDate before) {
    if (after != null && before != null) {
      var dateRange = String.format(FORMAT_QUALIFIER_RANGE, field, getDateAsIsoDate(after), getDateAsIsoDate(before));
      parts.add(dateRange);
    } else if (after != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, field, getDateAsIsoDate(after)));
    } else if (before != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, field, getDateAsIsoDate(before)));
    }
  }

  protected String getDateAsIsoDate(LocalDate localDate) {
    if (localDate == null) {
      return StringUtils.EMPTY;
    }
    return localDate.format(DateTimeFormatter.ISO_DATE);
  }

  public static abstract class CriteriaBuilder<T extends SearchCriteria, B> {
    protected final List<String> parts = new ArrayList<>();
    protected T criteria;
    protected String keywords;

    protected abstract B builder();

    /**
     * Add free-text keywords to search
     */
    public B keywords(String keywords) {
      this.keywords = keywords;
      criteria.keywords = keywords;
      return builder();
    }

    /**
     * Search in specific organization
     * 
     * @param org Organization name (e.g., "axonivy-market")
     */
    public B org(String org) {
      criteria.org = org;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ORG, org));
      return builder();
    }

    /**
     * Search in specific repository
     * 
     * @param owner Repository owner
     * @param repo  Repository name
     */
    public B repo(String owner, String repo) {
      String fullName = String.format(FORMAT_PATH, owner, repo);
      criteria.repo = fullName;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, REPO, fullName));
      return builder();
    }

    /**
     * Search in user's repositories
     */
    public B user(String username) {
      criteria.user = username;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, USER, username));
      return builder();
    }

    /**
     * Search by creation date
     */
    public B created(LocalDate date) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, CREATED, date.format(DateTimeFormatter.ISO_DATE)));
      return builder();
    }

    public B createdAfter(LocalDate date) {
      criteria.createdAfter = date;
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, CREATED, date.format(DateTimeFormatter.ISO_DATE)));
      return builder();
    }

    public B createdBefore(LocalDate date) {
      criteria.createdBefore = date;
      parts.add(String.format(FORMAT_QUALIFIER_LESS, CREATED, date.format(DateTimeFormatter.ISO_DATE)));
      return builder();
    }

    public B createdBetween(LocalDate from, LocalDate to) {
      criteria.createdAfter = from;
      criteria.createdBefore = to;
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, CREATED, from.format(DateTimeFormatter.ISO_DATE),
          to.format(DateTimeFormatter.ISO_DATE)));
      return builder();
    }

    /**
     * Search by updated date
     */
    public B updated(String date) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, UPDATED, date));
      return builder();
    }

    /**
     * Search public issues
     */
    public B isPublic() {
      criteria.isPublic = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, PUBLIC));
      return builder();
    }

    /**
     * Search protected issues
     */
    public B isPrivate() {
      criteria.isPrivate = true;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, PRIVATE));
      return builder();
    }

    /**
     * Search by repository language
     */
    public B language(String language) {
      criteria.language = language;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, LANGUAGE, language));
      return builder();
    }

    /**
     * Include archived repositories
     */
    public B archived(boolean archived) {
      criteria.archived = archived;
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, ARCHIVED, String.valueOf(archived)));
      return builder();
    }

    /**
     * Add raw query string part
     */
    public B raw(String queryPart) {
      criteria.rawQuery = queryPart;
      parts.add(queryPart);
      return builder();
    }

    /**
     * Set sort field
     */
    public B sort(String sort) {
      criteria.sort = sort;
      return builder();
    }

    public T build() {
      List<String> allParts = new ArrayList<>();
      if (keywords != null && !keywords.isEmpty()) {
        allParts.add(keywords);
      }
      allParts.addAll(parts);
      String query = allParts.stream().filter(StringUtils::isNoneBlank).collect(Collectors.joining(StringUtils.SPACE));

      criteria.query = query;
      return criteria;
    }

    public String buildString() {
      return build().getQuery();
    }
  }
}