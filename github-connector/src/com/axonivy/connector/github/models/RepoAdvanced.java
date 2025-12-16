package com.axonivy.connector.github.models;

import java.math.BigInteger;
import java.util.List;

import com.axonivy.connector.github.converter.JSONConverter;
import com.github.api.client.InlineResponse20033;
import com.github.api.client.Repository;

public class RepoAdvanced extends InlineResponse20033 {

  public RepoAdvanced(InlineResponse20033 inlineResponse20033) {
    setTotalCount(inlineResponse20033.getTotalCount());
    setItems(inlineResponse20033.getItems());
    setIncompleteResults(inlineResponse20033.getIncompleteResults());
  }
  
  public BigInteger getTotalCountValue() {
    if (getTotalCount() == null) {
      return BigInteger.ZERO;
    }
    return new BigInteger(getTotalCount().toString());
  }

  public List<Repository> getRepoItems() {
    if (getItems() == null) {
      return List.of();
    }
    return JSONConverter.convertToListDirect(getItems(), Repository.class);
  }
}
