package com.axonivy.connector.github.models;

import java.util.List;

import com.github.api.client.InlineResponse20016;
import com.github.api.client.PullRequestSimple;

public class WorkflowRunAdvanced extends InlineResponse20016 {

    public WorkflowRunAdvanced(InlineResponse20016 inlineResponse20016) {
      setTotalCount(inlineResponse20016.getTotalCount());
      setWorkflowRuns(inlineResponse20016.getWorkflowRuns());
    }
}
