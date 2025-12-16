package com.axonivy.connector.github.service;

import com.axonivy.connector.github.constant.GitHubConstants;

import ch.ivyteam.ivy.process.call.SubProcessCall;
import ch.ivyteam.ivy.process.call.SubProcessCallStart;
import ch.ivyteam.ivy.process.call.SubProcessCallStartParamCaller;

public abstract class AbstractGitHubService {

  protected abstract String getProcessName();

  /**
   * Create a SubProcessCallStart by the name of start callable process
   * @param startName is the name of start callable process
   * */
  protected SubProcessCallStart createCallSubProcessWithStartPath(String startName) {
    return SubProcessCall.withPath(getProcessName())
        .withStartName(startName);
  }

  /**
   * Create a SubProcessCallStartParamCaller with default params: page and pageSize
   * @param startName is the name of start callable process
   * @param page is the page number of the results to fetch
   * @param pageSize is the number of results per page (max 100)
   * */
  protected SubProcessCallStartParamCaller createCallSubProcessWithDefaultParams(String startName, int page, int pageSize) {
    return createCallSubProcessWithStartPath(startName)
        .withParam(GitHubConstants.PAGE, page)
        .withParam(GitHubConstants.PAGE_SIZE, pageSize);
  }

}
