package com.axonivy.connector.github.test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.github.GitHubRepoData;
import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.models.RepositorySearch;
import com.axonivy.connector.github.models.criteria.SearchRepositoryCriteria;
import com.axonivy.utils.e2etest.context.MultiEnvironmentContextProvider;
import com.github.api.client.Repository;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;

@IvyProcessTest(enableWebServer = true)
@ExtendWith(MultiEnvironmentContextProvider.class)
public class RepositoryTest extends BaseSetup {
  
  private static final BpmProcess repoProcess = BpmProcess.path("GitHubRepo");

  @TestTemplate
  public void testGetUserRepos(BpmClient bpmClient, ExtensionContext context) {
    BpmElement userRepoStart = repoProcess.elementName("getUserRepos(String,String,String,String,String,Integer,Integer,OffsetDateTime,OffsetDateTime)");
    ExecutionResult result = bpmClient.start().subProcess(userRepoStart)
        .withParam(GitHubConstants.PAGE, 1)
        .withParam(GitHubConstants.PAGE_SIZE, 10)
        .execute();

    GitHubRepoData data = result.data().last();
    if (isRealTest) {
      assertTrue(HttpStatus.SC_OK == data.getStatus(), "The server returns exception code instead of 200");
    }
    List<Repository> repos = data.getRepos();
    assertNotNull(repos, "The repos is null");
    assertNotEquals(0, repos.size(), "The repos is empty");
  }

  @TestTemplate
  public void testSearchRepos(BpmClient bpmClient, ExtensionContext context) {
    BpmElement searchReposStart = repoProcess.elementName("searchRepos(String,String,Integer,Integer)");
    ExecutionResult result = bpmClient.start().subProcess(searchReposStart)
        .withParam(GitHubConstants.QUERY, SearchRepositoryCriteria.builder().isPublic().buildString())
        .withParam(GitHubConstants.PAGE, 1)
        .withParam(GitHubConstants.PAGE_SIZE, 10)
        .execute();

    GitHubRepoData data = result.data().last();
    if (isRealTest) {
      assertTrue(HttpStatus.SC_OK == data.getStatus(), "The server returns exception code instead of 200");
    }
    RepositorySearch repoSearch = data.getRepositorySearch();
    assertNotNull(repoSearch, "The repos is null");
    assertNotEquals(0, repoSearch.getTotalCountValue(), "The total count of search is empty");
    assertNotEquals(0, repoSearch.getRepoItems().size(), "The repos of search is empty");
  }

}
