package com.axonivy.connector.github.models;

import java.math.BigInteger;
import java.util.List;

import com.axonivy.connector.github.converter.JSONConverter;
import com.github.api.client.InlineResponse20031;
import com.github.api.client.Issue;

public class IssueSearch extends InlineResponse20031 {
  
  public IssueSearch(InlineResponse20031 response20031) {
    setItems(response20031.getItems());
    setTotalCount(response20031.getTotalCount());
    setIncompleteResults(response20031.getIncompleteResults());
  }

  public BigInteger getTotalCountValue() {
    if (getTotalCount() == null) {
      return BigInteger.ZERO;
    }
    return new BigInteger(getTotalCount().toString());
  }

  public List<Issue> getIssueItems() {
    if (getItems() == null) {
      return List.of();
    }
    return JSONConverter.convertListObjectsToNewList(getItems(), Issue.class);
  }
}
