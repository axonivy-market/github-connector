package com.axonivy.connector.github.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import com.axonivy.connector.github.constant.GitHubConstants;

public class GitHubApiUtils {
  private static final String REPO = "https://api.github.com/repos/";

  private GitHubApiUtils() {
  }

  public static String extractRepoOwner(String repoUrl) {
    validateRepoUrl(repoUrl);
    var repoRemoved = Strings.CS.remove(repoUrl, REPO);
    var repoArray = repoRemoved.split(GitHubConstants.SLASH);
    return repoArray.length > 0 ? repoArray[0] : StringUtils.EMPTY;
  }

  public static String extractRepoName(String repoUrl) {
    var repoRemoved = Strings.CS.remove(repoUrl, REPO);
    var repoArray = repoRemoved.split(GitHubConstants.SLASH);
    return repoArray.length > 1 ? repoArray[1] : StringUtils.EMPTY;
  }

  private static void validateRepoUrl(String repoUrl) {
    ObjectUtils.requireNonEmpty(repoUrl, "Repo URL must not be empty");
    if (!repoUrl.startsWith(REPO)) {
      throw new IllegalArgumentException("Repo URL must starts with " + REPO);
    }
  }
}
