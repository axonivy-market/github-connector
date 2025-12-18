package com.axonivy.connector.github.models;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.converter.JSONConverter;
import com.github.api.client.InlineResponse20016;
import com.github.api.client.WorkflowRun;

public class WorkflowRunAdvanced extends InlineResponse20016 {

  private String lastWorkflowRunStatus;
  private List<WorkflowRun> workflowRunItems;

  public WorkflowRunAdvanced(InlineResponse20016 inlineResponse20016) {
    setTotalCount(inlineResponse20016.getTotalCount());
    setWorkflowRuns(inlineResponse20016.getWorkflowRuns());
    buildWorkflowRunItemsAndLastStatus();
  }

  public List<WorkflowRun> buildWorkflowRunItemsAndLastStatus() {
    if (getWorkflowRuns() == null) {
      return List.of();
    }
    List<WorkflowRun> workflowRuns = JSONConverter.convertToList(getWorkflowRuns(), WorkflowRun.class);
    lastWorkflowRunStatus = CollectionUtils.isEmpty(workflowRuns) ? StringUtils.EMPTY
        : String.valueOf(workflowRuns.getFirst().getConclusion());
    return workflowRuns;
  }

  public BigInteger getTotalCountValue() {
    if (getTotalCount() == null) {
      return BigInteger.ZERO;
    }
    return new BigInteger(getTotalCount().toString());
  }

  public List<WorkflowRun> getWorkflowRunItems() {
    return workflowRunItems;
  }

  public String getLastWorkflowRunStatus() {
    return lastWorkflowRunStatus;
  }

  public void setLastWorkflowRunStatus(String lastWorkflowRunStatus) {
    this.lastWorkflowRunStatus = lastWorkflowRunStatus;
  }
}
