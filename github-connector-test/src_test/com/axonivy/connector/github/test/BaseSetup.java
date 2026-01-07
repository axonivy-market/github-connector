package com.axonivy.connector.github.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.axonivy.connector.github.enums.Variable;
import com.axonivy.utils.e2etest.utils.E2ETestUtils;

import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.rest.client.RestClient;
import ch.ivyteam.ivy.rest.client.RestClient.Builder;
import ch.ivyteam.ivy.rest.client.RestClientFeature;
import ch.ivyteam.ivy.rest.client.RestClients;

public abstract class BaseSetup {

  protected static final String TEST_REPO_NAME = "testing-api";
  protected static final String TEST_ORG_NAME = "internal-testing";
  protected static final String GH_REST_CLIENT_NAME = "github (GitHub v3 REST API)";
  protected boolean isRealTest;

  @BeforeEach
  public void beforeEach(ExtensionContext context, AppFixture fixture, IApplication app) {
    isRealTest = true;
    E2ETestUtils.determineConfigForContext(context.getDisplayName(), runRealEnv(fixture), runMockEnv(fixture, app));
  }

  protected Runnable runRealEnv(AppFixture fixture) {
    return () -> {
      String org = System.getProperty(Variable.ORG.getKey());
      String accessToken = System.getProperty(Variable.ACCESS_TOKEN.getKey());

      fixture.var(Variable.ORG.getKey(), org);
      fixture.var(Variable.ACCESS_TOKEN.getKey(), accessToken);
    };
  }

  protected Runnable runMockEnv(AppFixture fixture, IApplication app) {
    return () -> {
      fixture.var(Variable.ORG.getKey(), TEST_ORG_NAME);
      fixture.var(Variable.ACCESS_TOKEN.getKey(), Variable.ACCESS_TOKEN.getKey());

      RestClient restClient = RestClients.of(app).find(GH_REST_CLIENT_NAME);
      Builder builder = RestClient.create(restClient.name())
          .uuid(restClient.uniqueId())
          .uri("http://{ivy.engine.host}:{ivy.engine.http.port}/{ivy.request.application}/api/githubMock")
          .description(restClient.description())
          .properties(restClient.properties());

      for (RestClientFeature feature : restClient.features()) {
        builder.feature(feature.clazz());
      }

      restClient = builder.toRestClient();
      RestClients.of(app).set(restClient);
    };
  }
}