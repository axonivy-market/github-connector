package connector.github.test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.axonivy.ivy.webtest.primeui.PrimeUi;
import com.axonivy.ivy.webtest.primeui.widget.Table;

@IvyWebTest
public class WebTestReposIT {

  @Test
  public void checkRepos() {
    open(EngineUrl
            .createProcessUrl("/github-connector-test/17FB22AD8C155AB0/start.ivp?environment=dev-axonivy"));

    open(EngineUrl.createProcessUrl("/github-connector-demo/17F92CE6CA3FD67B/repoList.ivp"));

    Table table = PrimeUi.table(By.id("form:reposTable"));
    table.contains("axonivy");
    table.contains("angular-demo");

    $(By.id("form:proceed")).shouldBe(visible).click();
  }

}