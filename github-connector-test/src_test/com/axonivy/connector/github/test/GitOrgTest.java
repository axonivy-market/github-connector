package com.axonivy.connector.github.test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.github.GitHubOrgData;
import com.axonivy.connector.github.constant.GitHubConstants;
import com.axonivy.connector.github.constant.GitHubParamConstants;
import com.axonivy.utils.e2etest.context.MultiEnvironmentContextProvider;
import com.github.api.client.SimpleUser;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;

@IvyProcessTest(enableWebServer = true)
@ExtendWith(MultiEnvironmentContextProvider.class)
public class GitOrgTest extends BaseSetup {

  private static final BpmProcess orgProcess = BpmProcess.path("GitHubOrg");

  @TestTemplate
  public void testGetOrgMembers(BpmClient bpmClient, ExtensionContext context) {
    BpmElement orgMembersStart = orgProcess.elementName("getOrgMembers(String,String,String,Integer,Integer)");
    ExecutionResult result = bpmClient.start().subProcess(orgMembersStart)
        .withParam(GitHubParamConstants.ORG, TEST_ORG_NAME)
        .withParam(GitHubConstants.PAGE, 1)
        .withParam(GitHubConstants.PAGE_SIZE, 10).execute();

    GitHubOrgData data = result.data().last();
    if (isRealTest) {
      assertTrue(HttpStatus.SC_OK == data.getStatus(), "The server returns exception code instead of 200");
    } else {
      List<SimpleUser> simpleUsers = data.getUsers();
      assertNotNull(simpleUsers, "The SimpleUser is null");
      assertNotEquals(0, simpleUsers.size(), "The total users is empty");
    }
  }

}
