package com.axonivy.connector.github.models.criteria;

import static com.axonivy.connector.github.constant.GitHubParamConstants.DESCRIPTION;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FOLLOWERS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORK;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORKS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_GREATER;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_LESS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_RANGE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FORMAT_QUALIFIER_VALUE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.FUNDING_FILE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.GOOD_FIRST_ISSUES;
import static com.axonivy.connector.github.constant.GitHubParamConstants.HAS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.HELP_WANTED_ISSUES;
import static com.axonivy.connector.github.constant.GitHubParamConstants.IN;
import static com.axonivy.connector.github.constant.GitHubParamConstants.IS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.LICENSE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.MIRROR;
import static com.axonivy.connector.github.constant.GitHubParamConstants.NAME;
import static com.axonivy.connector.github.constant.GitHubParamConstants.PUSHED;
import static com.axonivy.connector.github.constant.GitHubParamConstants.README;
import static com.axonivy.connector.github.constant.GitHubParamConstants.SIZE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.SPONSORABLE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.STARS;
import static com.axonivy.connector.github.constant.GitHubParamConstants.TEMPLATE;
import static com.axonivy.connector.github.constant.GitHubParamConstants.TOPIC;
import static com.axonivy.connector.github.constant.GitHubParamConstants.TOPICS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.constant.GitHubConstants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchRepositoryCriteria extends SearchCriteria {
  protected String inName;
  protected String inDescription;
  protected String inReadme;
  protected List<String> inTopics;
  protected String topic;
  protected String topics;
  protected String topicsMin;
  protected String topicsMax;
  protected String license;
  protected String stars;
  protected String starsMin;
  protected String starsMax;
  protected String forks;
  protected String forksMin;
  protected String forksMax;
  protected String size;
  protected String sizeMin;
  protected String sizeMax;
  protected String followers;
  protected String followersMin;
  protected String followersMax;
  protected LocalDate pushedAfter;
  protected LocalDate pushedBefore;
  protected Boolean fork;
  protected Boolean isMirror;
  protected Boolean isTemplate;
  protected String goodFirstIssues;
  protected String helpWantedIssues;
  protected Boolean isSponsorable;
  protected Boolean hasFundingFile;

  public SearchRepositoryCriteria() {
    this.inTopics = new ArrayList<>();
  }

  @Override
  protected List<String> buildQueryFromSelfProperties() {
    List<String> parts = new ArrayList<>();
    // Search scope (in qualifiers)
    List<String> inParts = new ArrayList<>();
    if (StringUtils.isNotBlank(inName)) {
      inParts.add(NAME);
    }
    if (StringUtils.isNotBlank(inDescription)) {
      inParts.add(DESCRIPTION);
    }
    if (StringUtils.isNotBlank(inReadme)) {
      inParts.add(README);
    }
    if (inTopics != null && !inTopics.isEmpty()) {
      inParts.add(TOPICS);
    }
    if (!inParts.isEmpty()) {
      String inValue = String.join(GitHubConstants.COMMA, inParts);
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IN, inValue));
    }
    // Topic
    if (StringUtils.isNotBlank(topic)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, TOPIC, topic));
    }
    // Number of topics
    if (topics != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, TOPICS, topics));
    } else if (topicsMin != null && topicsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, TOPICS, topicsMin, topicsMax));
    } else if (topicsMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, TOPICS, topicsMin));
    } else if (topicsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, TOPICS, topicsMax));
    }
    // Stars
    if (stars != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, STARS, stars));
    } else if (starsMin != null && starsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, STARS, starsMin, starsMax));
    } else if (starsMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, STARS, starsMin));
    } else if (starsMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, STARS, starsMax));
    }
    // Forks
    if (forks != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, FORKS, forks));
    } else if (forksMin != null && forksMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, FORKS, forksMin, forksMax));
    } else if (forksMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, FORKS, forksMin));
    } else if (forksMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, FORKS, forksMax));
    }
    // Size (in KB)
    if (size != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, SIZE, size));
    } else if (sizeMin != null && sizeMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, SIZE, sizeMin, sizeMax));
    } else if (sizeMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, SIZE, sizeMin));
    } else if (sizeMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, SIZE, sizeMax));
    }
    // Followers
    if (followers != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, FOLLOWERS, followers));
    } else if (followersMin != null && followersMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_RANGE, FOLLOWERS, followersMin, followersMax));
    } else if (followersMin != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, FOLLOWERS, followersMin));
    } else if (followersMax != null) {
      parts.add(String.format(FORMAT_QUALIFIER_LESS, FOLLOWERS, followersMax));
    }
    // License
    if (StringUtils.isNotBlank(license)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, LICENSE, license));
    }
    // Dates
    addDateRange(parts, PUSHED, pushedAfter, pushedBefore);
    // Fork
    if (fork != null) {
      String forkValue = fork ? "true" : "only";
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, FORK, forkValue));
    }
    // Mirror
    if (isMirror != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, MIRROR, String.valueOf(isMirror)));
    }
    // Template
    if (isTemplate != null) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, TEMPLATE, String.valueOf(isTemplate)));
    }
    // Good first issues
    if (goodFirstIssues != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, GOOD_FIRST_ISSUES, goodFirstIssues));
    }
    // Help wanted issues
    if (helpWantedIssues != null) {
      parts.add(String.format(FORMAT_QUALIFIER_GREATER, HELP_WANTED_ISSUES, helpWantedIssues));
    }
    // Sponsorable
    if (Boolean.TRUE.equals(isSponsorable)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, IS, SPONSORABLE));
    }
    // Has funding file
    if (Boolean.TRUE.equals(hasFundingFile)) {
      parts.add(String.format(FORMAT_QUALIFIER_VALUE, HAS, FUNDING_FILE));
    }
    return parts;
  }

  @Override
  public String toString() {
    return getQuery();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder from(SearchRepositoryCriteria criteria) {
    Builder builder = new Builder();
    builder.criteria = criteria;
    return builder;
  }

  public static class Builder extends CriteriaBuilder<SearchRepositoryCriteria, Builder> {
    public Builder() {
      criteria = new SearchRepositoryCriteria();
    }

    @Override
    protected Builder builderType() {
      return this;
    }

    /**
     * Search in repository name
     */
    public Builder inName(String text) {
      criteria.inName = text;
      return this;
    }

    /**
     * Search in repository description
     */
    public Builder inDescription(String text) {
      criteria.inDescription = text;
      return this;
    }

    /**
     * Search in repository README
     */
    public Builder inReadme(String text) {
      criteria.inReadme = text;
      return this;
    }

    /**
     * Add a topic to search for in repository topics
     */
    public Builder inTopics(String topic) {
      if (criteria.inTopics == null) {
        criteria.inTopics = new ArrayList<>();
      }
      criteria.inTopics.add(topic);
      return this;
    }

    /**
     * Search by repository topic
     */
    public Builder topic(String topic) {
      criteria.topic = topic;
      return this;
    }

    /**
     * Search by exact number of topics
     */
    public Builder topics(int count) {
      criteria.topics = String.valueOf(count);
      return this;
    }

    /**
     * Search by number of topics (greater than)
     */
    public Builder topicsGreaterThan(int count) {
      criteria.topicsMin = String.valueOf(count);
      return this;
    }

    /**
     * Search by number of topics (less than)
     */
    public Builder topicsLessThan(int count) {
      criteria.topicsMax = String.valueOf(count);
      return this;
    }

    /**
     * Search by number of topics (range)
     */
    public Builder topicsRange(int min, int max) {
      criteria.topicsMin = String.valueOf(min);
      criteria.topicsMax = String.valueOf(max);
      return this;
    }

    /**
     * Search by license
     */
    public Builder license(String license) {
      criteria.license = license;
      return this;
    }

    /**
     * Search by exact number of stars
     */
    public Builder stars(int count) {
      criteria.stars = String.valueOf(count);
      return this;
    }

    /**
     * Search by stars (greater than or equal to)
     */
    public Builder starsGreaterThan(int count) {
      criteria.starsMin = String.valueOf(count);
      return this;
    }

    /**
     * Search by stars (less than or equal to)
     */
    public Builder starsLessThan(int count) {
      criteria.starsMax = String.valueOf(count);
      return this;
    }

    /**
     * Search by stars (range)
     */
    public Builder starsRange(int min, int max) {
      criteria.starsMin = String.valueOf(min);
      criteria.starsMax = String.valueOf(max);
      return this;
    }

    /**
     * Search by exact number of forks
     */
    public Builder forks(int count) {
      criteria.forks = String.valueOf(count);
      return this;
    }

    /**
     * Search by forks (greater than or equal to)
     */
    public Builder forksGreaterThan(int count) {
      criteria.forksMin = String.valueOf(count);
      return this;
    }

    /**
     * Search by forks (less than or equal to)
     */
    public Builder forksLessThan(int count) {
      criteria.forksMax = String.valueOf(count);
      return this;
    }

    /**
     * Search by forks (range)
     */
    public Builder forksRange(int min, int max) {
      criteria.forksMin = String.valueOf(min);
      criteria.forksMax = String.valueOf(max);
      return this;
    }

    /**
     * Search by exact repository size in KB
     */
    public Builder size(int sizeKb) {
      criteria.size = String.valueOf(sizeKb);
      return this;
    }

    /**
     * Search by repository size (greater than or equal to) in KB
     */
    public Builder sizeGreaterThan(int sizeKb) {
      criteria.sizeMin = String.valueOf(sizeKb);
      return this;
    }

    /**
     * Search by repository size (less than or equal to) in KB
     */
    public Builder sizeLessThan(int sizeKb) {
      criteria.sizeMax = String.valueOf(sizeKb);
      return this;
    }

    /**
     * Search by repository size (range) in KB
     */
    public Builder sizeRange(int minKb, int maxKb) {
      criteria.sizeMin = String.valueOf(minKb);
      criteria.sizeMax = String.valueOf(maxKb);
      return this;
    }

    /**
     * Search by exact number of followers
     */
    public Builder followers(int count) {
      criteria.followers = String.valueOf(count);
      return this;
    }

    /**
     * Search by followers (greater than or equal to)
     */
    public Builder followersGreaterThan(int count) {
      criteria.followersMin = String.valueOf(count);
      return this;
    }

    /**
     * Search by followers (less than or equal to)
     */
    public Builder followersLessThan(int count) {
      criteria.followersMax = String.valueOf(count);
      return this;
    }

    /**
     * Search by followers (range)
     */
    public Builder followersRange(int min, int max) {
      criteria.followersMin = String.valueOf(min);
      criteria.followersMax = String.valueOf(max);
      return this;
    }

    /**
     * Search by last push date (after)
     */
    public Builder pushedAfter(LocalDate date) {
      criteria.pushedAfter = date;
      return this;
    }

    /**
     * Search by last push date (before)
     */
    public Builder pushedBefore(LocalDate date) {
      criteria.pushedBefore = date;
      return this;
    }

    /**
     * Search by last push date (range)
     */
    public Builder pushedBetween(LocalDate from, LocalDate to) {
      criteria.pushedAfter = from;
      criteria.pushedBefore = to;
      return this;
    }

    /**
     * Include forked repositories in search (fork:true)
     */
    public Builder includeForks() {
      criteria.fork = true;
      return this;
    }

    /**
     * Search only forked repositories (fork:only)
     */
    public Builder onlyForks() {
      criteria.fork = false;
      return this;
    }

    /**
     * Search mirror repositories
     */
    public Builder mirror(boolean isMirror) {
      criteria.isMirror = isMirror;
      return this;
    }

    /**
     * Search template repositories
     */
    public Builder template(boolean isTemplate) {
      criteria.isTemplate = isTemplate;
      return this;
    }

    /**
     * Search by minimum number of good first issues
     */
    public Builder goodFirstIssuesGreaterThan(int count) {
      criteria.goodFirstIssues = String.valueOf(count);
      return this;
    }

    /**
     * Search by minimum number of help wanted issues
     */
    public Builder helpWantedIssuesGreaterThan(int count) {
      criteria.helpWantedIssues = String.valueOf(count);
      return this;
    }

    /**
     * Search repositories whose owners can be sponsored
     */
    public Builder sponsorable() {
      criteria.isSponsorable = true;
      return this;
    }

    /**
     * Search repositories that have a funding file
     */
    public Builder hasFundingFile() {
      criteria.hasFundingFile = true;
      return this;
    }
  }
}