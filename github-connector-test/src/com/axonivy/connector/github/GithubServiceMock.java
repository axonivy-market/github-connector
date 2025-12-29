package com.axonivy.connector.github;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.axonivy.connector.github.constant.GitHubConstants;

import io.swagger.v3.oas.annotations.Hidden;

@Path("githubMock")
@PermitAll
@Hidden
public class GithubServiceMock {

  private static final String LINK_PATTERN = "<https://api.github.com/%s?per_page=10&page=1>; rel=\"last\"";

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("user/repos")
  public Response repos() {
    var userRepoLink = LINK_PATTERN.formatted("user/repos");
    Response response = Response.ok(load("json/repos.json"), MediaType.APPLICATION_JSON)
        .header(GitHubConstants.LINK, userRepoLink).build();
    return response;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("search/repositories")
  public Response searchRepos(@QueryParam("q") String query) {
    var userRepoLink = LINK_PATTERN.formatted("search/repositories?q=org%3Atest-org+is%3Apublic");
    Response response = Response.ok(load("json/search-repos.json"), MediaType.APPLICATION_JSON)
        .header(GitHubConstants.LINK, userRepoLink).build();
    return response;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/repos/{ownerName}/{repoName}/pulls")
  public Response repos(@PathParam("ownerName") String ownerName, @PathParam("repoName") String repoName) {
    var repoPullUri = "repos/" + ownerName + "/" + repoName + "/pulls";
    var pullsLink = LINK_PATTERN.formatted(repoPullUri);
    Response response = Response.ok(load("json/pull-requests.json"), MediaType.APPLICATION_JSON)
        .header(GitHubConstants.LINK, pullsLink).build();
    return response;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/repos/{ownerName}/{repoName}/actions/runs")
  public Response workflowRun(@PathParam("ownerName") String ownerName, @PathParam("repoName") String repoName) {
    var workflowRunUri = "repos/" + ownerName + "/" + repoName + "/actions/runs";
    var actionsLink = LINK_PATTERN.formatted(workflowRunUri);
    Response response = Response.ok(load("json/workflow-runs.json"), MediaType.APPLICATION_JSON)
        .header(GitHubConstants.LINK, actionsLink).build();
    return response;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("orgs/{org}/members")
  public Response members(@PathParam("org") String org) {
    var orgMembersUri = "orgs/" + org + "/members";
    var orgsLink = LINK_PATTERN.formatted(orgMembersUri);
    Response response = Response.ok(load("json/members.json"), MediaType.APPLICATION_JSON)
        .header(GitHubConstants.LINK, orgsLink).build();
    return response;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("search/issues")
  public Response searchIssues(@QueryParam("q") String query) {
    var searchIssueLink = LINK_PATTERN.formatted("search/issues?q=org%3Atest-org");
    Response response = Response.ok(load("json/search-issues.json"), MediaType.APPLICATION_JSON)
        .header(GitHubConstants.LINK, searchIssueLink).build();
    return response;
  }

  private static String load(String path) {
    try (InputStream is = GithubServiceMock.class.getResourceAsStream(path)) {
      return IOUtils.toString(is, StandardCharsets.UTF_8);
    } catch (IOException ex) {
      throw new RuntimeException("Failed to read resource: " + path);
    }
  }
}
