package com.axonivy.connector.github.test;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.axonivy.ivy.webtest.primeui.PrimeUi;
import com.axonivy.ivy.webtest.primeui.widget.Table;
import com.codeborne.selenide.Condition;

@IvyWebTest(headless = false)
public class ScannerConfigTest {

  @BeforeEach
  void loginAsDeveloper() {
    open(EngineUrl.base() + "/dev-workflow-ui/faces/loginTable.xhtml?originalUrl=home.xhtml");
    Table table = PrimeUi.table(By.id("loginTable:users"));
    var firstRow = table.row(0);
    firstRow.$$("td").findBy(Condition.text("Developer")).shouldBe(enabled).click();
  }

  @Test
  public void testScannerConfigWithNoOrg() {
    open(EngineUrl.createProcessUrl("/github-connector/19B10B247B9B0521/startConfig.ivp"));

    var configForm = $(By.id("scanner-form")).shouldBe(visible);
    var orgInput = configForm.$(By.id("scanner-form:org"));
    orgInput.clear();

    configForm.$(By.id("scanner-form:proceed")).shouldBe(visible).click();
    var message = configForm.$(By.id("scanner-form:scanner-config-messages")).shouldBe(visible);
    assertTrue(message.text().contains("Organization name must not be empty"), "No Error message when no org is enter");
  }

}