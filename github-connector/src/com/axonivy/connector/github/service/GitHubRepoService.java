package com.axonivy.connector.github.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.models.PullRequestsWithCount;
import com.axonivy.connector.github.models.RepoAdvanced;
import com.axonivy.connector.github.models.WorkflowRunAdvanced;
import com.github.api.client.PullRequestSimple;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCallResult;
import ch.ivyteam.ivy.process.call.SubProcessCallStartParamCaller;

public class GitHubRepoService extends AbstractGitHubService {
  private static GitHubRepoService instance;

  private GitHubRepoService() { }

  public static GitHubRepoService getInstance() {
    if (instance == null) {
      instance = new GitHubRepoService();
    }
    return instance;
  }

  public RepoAdvanced fetchReposWithCount(int page, int pageSize) {
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams("searchUserRepos", page, pageSize);
    SubProcessCallResult result = caller.call();
    return result.get("repoAdvanced", RepoAdvanced.class);
  }

  public WorkflowRunAdvanced getWorkflowRuns(String owner, String repo, int page, int pageSize) {
    SubProcessCallStartParamCaller caller = createCallSubProcessWithDefaultParams("getActionsRun", page, pageSize);
    caller.withParam("owner", owner);
    caller.withParam("repo", repo);
    SubProcessCallResult result = caller.call();
    return result.get("workflowRunAdvanced", WorkflowRunAdvanced.class);
  }

  public PullRequestsWithCount getPullRequests(String fullRepoName) {
    WebTarget target = restClient.path("/repos/" + fullRepoName + "/pulls").queryParam("per_page", perPage);
    var response = target.request(MediaType.APPLICATION_JSON).get();
    var header = response.getHeaderString("Link");
    List<PullRequestSimple> prs = response.readEntity(new GenericType<List<PullRequestSimple>>() {});
    if (!prs.isEmpty() && header == null) {
      return new PullRequestsWithCount(prs.size(), prs);
    } else if (!prs.isEmpty()) {
      int count = getElementsCount(header, perPage, target);
      return new PullRequestsWithCount(count, prs);
    }
    return new PullRequestsWithCount(0, prs);
  }

  public static int getReposCount() {
    WebTarget restClient = Ivy.rest().client(UUID.fromString("4895b78f-4d15-49f6-9754-de015d91d52e"));
    WebTarget target = restClient.path("/user/repos")
            .queryParam("per_page", 1)
            .queryParam("page", 1);
    var response = target.request(MediaType.APPLICATION_JSON).get();
    var header = response.getHeaderString("Link");
    return getElementsCount(header, 1, target);
  }

  private static int getElementsCount(String header, int pageSize, WebTarget target) {
    List<String> elements = Arrays.asList(header.split(","));

    for (String element : elements) {
      if (element.contains("rel=\"last\"")) {
        String link = StringUtils.substringBetween(element, "?", ">");
        Map<String, String> parameters = getParamMap(link);
        int pages = Integer.parseInt(parameters.get("page"));
        return pages * pageSize;
      }
    }
    Map<String, String> parameters = getParamMap(target.getUri().toString());
    int page = Integer.parseInt(parameters.get("page"));
    return page * pageSize;
  }

  private static Map<String, String> getParamMap(String link) {
    String[] params = link.split("&");
    Map<String, String> map = new HashMap<String, String>();

    for (String param : params) {
      String name = param.split("=")[0];
      String value = param.split("=")[1];
      map.put(name, value);
    }
    return map;
  }

  @Override
  protected String getProcessName() {
    return "GitHubRepo";
  }

}
