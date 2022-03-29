package connector.github.test;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.axonivy.ivy.webtest.primeui.PrimeUi;
import com.axonivy.ivy.webtest.primeui.widget.Table;

@IvyWebTest
public class WebTestReposIT {

  @BeforeEach
  public void beforeEach() {
    open(EngineUrl
            .createProcessUrl("/github-connector-test/17FB22AD8C155AB0/start.ivp?environment=dev-axonivy"));
  }

  @Test
  public void checkRepos() {
    open(EngineUrl.createProcessUrl("/github-connector-demo/17F92CE6CA3FD67B/repoList.ivp"));

    Table table = PrimeUi.table(By.id("form:reposTable"));
    table.contains("axonivy");
    table.contains("angular-demo");

    $(By.id("form:proceed")).shouldBe(visible).click();
  }

  @Test
  public void checkPullRequestsToggleSwitch() {
    open(EngineUrl.createProcessUrl("/github-connector-demo/17F92CE6CA3FD67B/repoList.ivp"));

    $(By.id("form:pullRequestsCheckbox")).shouldBe(disabled);
    $(By.id("form:reposTable:repoPullrequests")).shouldNotBe(visible);
    $(By.id("form:reposTable:repoIssues")).shouldHave(text("Issues/Pull requests"));

    $(By.id("form:pullRequestsCheckbox")).click();
    $(By.id("form:reposTable:repoIssues")).shouldHave(exactText("Issues"));
    $(By.id("form:reposTable:repoPullrequests")).shouldBe(visible);

    $(By.id("form:proceed")).shouldBe(visible).click();
  }

  @Test
  public void checkWorkflowsToggleSwitch() {
    open(EngineUrl.createProcessUrl("/github-connector-demo/17F92CE6CA3FD67B/repoList.ivp"));

    $(By.id("form:workflowRunsCheckbox")).shouldBe(enabled);
    $(By.id("form:reposTable:repoWorkflowStatus")).shouldBe(visible);
    $(By.id("form:workflowRunsCheckbox")).click();
    $(By.id("form:reposTable:repoWorkflowStatus")).shouldNotBe(visible);

    $(By.id("form:proceed")).shouldBe(visible).click();
  }
}