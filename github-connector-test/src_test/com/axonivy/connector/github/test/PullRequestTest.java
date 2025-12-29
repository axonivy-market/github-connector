package com.axonivy.connector.github.test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.github.GitHubPullRequestData;
import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.constant.GitHubParamConstants;
import com.axonivy.connector.github.models.PullRequestAdvanced;
import com.axonivy.utils.e2etest.context.MultiEnvironmentContextProvider;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;

@IvyProcessTest(enableWebServer = true)
@ExtendWith(MultiEnvironmentContextProvider.class)
public class PullRequestTest extends BaseSetup {

  private static final BpmProcess repoProcess = BpmProcess.path("GitHubPullRequest");

  @TestTemplate
  public void testGetPullRequest(BpmClient bpmClient, ExtensionContext context) {
    BpmElement pullRequestStart = repoProcess
        .elementName("getPullRequests(String,String,String,String,String,String,String,Integer,Integer)");
    ExecutionResult result = bpmClient.start().subProcess(pullRequestStart)
        .withParam(GitHubParamConstants.OWNER, "my-test")
        .withParam(GitHubParamConstants.REPO, "COD")
        .withParam(GitHubConstants.PAGE, 1)
        .withParam(GitHubConstants.PAGE_SIZE, 10).execute();

    GitHubPullRequestData data = result.data().last();
    if (isRealTest) {
      assertTrue(HttpStatus.SC_OK == data.getStatus(), "The server returns exception code instead of 200");
    } else {
      PullRequestAdvanced pullRequestAdvanced = data.getPullRequestAdvanced();
      assertNotNull(pullRequestAdvanced, "The PullRequestAdvanced is null");
      assertNotEquals(0, pullRequestAdvanced.getPullRequests(), "The pullrequest is empty");
    }
  }

}
