package com.axonivy.connector.github.test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.github.GitHubActionsData;
import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.constant.GitHubParamConstants;
import com.axonivy.connector.github.models.WorkflowRunAdvanced;
import com.axonivy.utils.e2etest.context.MultiEnvironmentContextProvider;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;

@IvyProcessTest(enableWebServer = true)
@ExtendWith(MultiEnvironmentContextProvider.class)
public class ActionsTest extends BaseSetup {

  private static final BpmProcess actionsProcess = BpmProcess.path("GitHubActions");

  @TestTemplate
  public void testGetWorkflowRun(BpmClient bpmClient, ExtensionContext context) {
    BpmElement workflowRunStart = actionsProcess.elementName("getActionsRun(String,String,Integer,Integer)");
    ExecutionResult result = bpmClient.start().subProcess(workflowRunStart)
        .withParam(GitHubParamConstants.OWNER, GitHubParamConstants.OWNER)
        .withParam(GitHubParamConstants.REPO, GitHubParamConstants.REPO)
        .withParam(GitHubConstants.PAGE, 1)
        .withParam(GitHubConstants.PAGE_SIZE, 10).execute();

    GitHubActionsData data = result.data().last();
    if (isRealTest) {
      assertTrue(HttpStatus.SC_OK == data.getStatus(), "The server returns exception code instead of 200");
    } else {
      WorkflowRunAdvanced workflowRunAdvanced = data.getWorkflowRunAdvanced();
      assertNotNull(workflowRunAdvanced, "The WorkflowRunAdvanced is null");
      assertNotEquals(0, workflowRunAdvanced.getTotalCountValue(), "The total count is empty");
      assertNotEquals(0, workflowRunAdvanced.getWorkflowRunItems(), "The workflow run item is empty");
      assertNotEquals(0, workflowRunAdvanced.getLastWorkflowRunStatus(), "The workflow run status is empty");
    }
  }

}
