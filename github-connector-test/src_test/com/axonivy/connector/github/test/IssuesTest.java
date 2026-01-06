package com.axonivy.connector.github.test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.github.GitHubIssueData;
import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.constant.GitHubParamConstants;
import com.axonivy.connector.github.models.IssueSearch;
import com.axonivy.connector.github.models.criteria.SearchIssueCriteria;
import com.axonivy.utils.e2etest.context.MultiEnvironmentContextProvider;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;

@IvyProcessTest(enableWebServer = true)
@ExtendWith(MultiEnvironmentContextProvider.class)
public class IssuesTest extends BaseSetup {

  private static final BpmProcess issueProcess = BpmProcess.path("GitHubIssue");

  @TestTemplate
  public void testSearchIssues(BpmClient bpmClient, ExtensionContext context) {
    BpmElement searchIssuesStart = issueProcess.elementName("searchIssues(String,String,String,Integer,Integer)");
    ExecutionResult result = bpmClient.start().subProcess(searchIssuesStart)
        .withParam(GitHubParamConstants.ORG, TEST_ORG_NAME)
        .withParam(GitHubConstants.QUERY, SearchIssueCriteria.builder().stateOpen().assigneeAny().buildString())
        .withParam(GitHubConstants.PAGE, 1)
        .withParam(GitHubConstants.PAGE_SIZE, 10).execute();

    GitHubIssueData data = result.data().last();
    if (isRealTest) {
      assertTrue(HttpStatus.SC_OK == data.getStatus(), "The server returns exception code instead of 200");
    } else {
      IssueSearch issueSearch = data.getIssuesSearch();
      assertNotNull(issueSearch, "The IssueSearch is null");
      assertNotEquals(0, issueSearch.getIssueItems().size(), "The total users is empty");
    }
  }

}
