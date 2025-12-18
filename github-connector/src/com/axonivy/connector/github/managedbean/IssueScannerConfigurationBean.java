package com.axonivy.connector.github.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.collections4.CollectionUtils;

import com.axonivy.connector.github.converter.JSONConverter;
import com.axonivy.connector.github.enums.IssueState;
import com.axonivy.connector.github.enums.Variable;
import com.axonivy.connector.github.models.IssueScannerConfig;
import com.axonivy.connector.github.util.VariableUtils;

@ManagedBean
@ViewScoped
public class IssueScannerConfigurationBean implements Serializable {

  private static final long serialVersionUID = 1L;
  private IssueScannerConfig issueScannerConfig;

  public static IssueScannerConfigurationBean getInstance() {
    var currentInstance = FacesContext.getCurrentInstance();
    return currentInstance.getApplication().evaluateExpressionGet(currentInstance,
        "#{issueScannerConfigurationBean}",
        IssueScannerConfigurationBean.class);
  }

  @PostConstruct
  public void init() {
    String configAsString = VariableUtils.getVariable(Variable.SEARCH_ISSUE_CRITERIA);
    issueScannerConfig = JSONConverter.convertToObject(configAsString, IssueScannerConfig.class);
    if (issueScannerConfig == null) {
      issueScannerConfig = new IssueScannerConfig();
    }
  }

  public void saveConfig() {
    if (issueScannerConfig == null) {
      return;
    }
    VariableUtils.setVariable(Variable.ORG, issueScannerConfig.getOrg());
    String config = JSONConverter.convertToString(issueScannerConfig);
    VariableUtils.setVariable(Variable.SEARCH_ISSUE_CRITERIA, config);
  }

  public List<String> completeIssueLabels(String query) {
    List<String> labels = this.issueScannerConfig.getLabels().stream().filter(label -> label.contains(query)).toList();
    return CollectionUtils.isEmpty(labels) ? List.of(query) : labels;
  }

  public List<String> getIssueStates() {
    return List.of(IssueState.OPEN, IssueState.CLOSED).stream().map(IssueState::getState).toList();
  }
  
  public IssueScannerConfig getIssueScannerConfig() {
    return issueScannerConfig;
  }

  public void setIssueScannerConfig(IssueScannerConfig issueScannerConfig) {
    this.issueScannerConfig = issueScannerConfig;
  }

}
