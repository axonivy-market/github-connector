package com.axonivy.connector.github.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import com.axonivy.connector.github.constant.GitHubConstants;

public class GitHubApiUtils {
  private static final String API_REPO = "https://api.github.com/repos/";

  private GitHubApiUtils() {
  }

  public static String extractRepoOwner(String repoUrl) {
    validateRepoUrl(repoUrl);
    var urlParts = extractRepoUrlParts(repoUrl);
    return urlParts.length > 0 ? urlParts[0] : StringUtils.EMPTY;
  }

  public static String extractRepoName(String repoUrl) {
    var urlParts = extractRepoUrlParts(repoUrl);
    return urlParts.length > 1 ? urlParts[1] : StringUtils.EMPTY;
  }

  private static String[] extractRepoUrlParts(String repoUrl) {
    var repoRemoved = Strings.CS.remove(repoUrl, API_REPO);
    var repoArray = repoRemoved.split(GitHubConstants.SLASH);
    return repoArray;
  }

  private static void validateRepoUrl(String repoUrl) {
    ObjectUtils.requireNonEmpty(repoUrl, "Repo URL must not be empty");
    if (!repoUrl.startsWith(API_REPO)) {
      throw new IllegalArgumentException("Repo URL must starts with " + API_REPO);
    }
  }
}
