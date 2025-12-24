package com.axonivy.connector.github.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.constant.GitHubConstants;

import ch.ivyteam.ivy.process.call.SubProcessCall;
import ch.ivyteam.ivy.process.call.SubProcessCallStart;
import ch.ivyteam.ivy.process.call.SubProcessCallStartParamCaller;

public abstract class AbstractGitHubService {
  
  private static final String LAST_REF = "rel=\"last\"";
  private static final String URI_PREFIX = "uri=";
  private static final String PER_PAGE = "per_page";

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

  public int getTotalCountFromHeader(Response response, int defaultCount) {
    var header = response.getHeaderString(GitHubConstants.LINK);
    if (header != null) {
      var uri = StringUtils.substringBetween(response.toString(), URI_PREFIX, GitHubConstants.COMMA);
      return getElementsCount(header, StringUtils.substringAfter(uri, GitHubConstants.QUESTION));
    }
    return defaultCount;
  }

  private int getElementsCount(String header, String uri) {
    List<String> elements = Arrays.asList(header.split(GitHubConstants.COMMA));
    Map<String, Integer> params = getParamMap(uri);
    int pageSize = params.get(PER_PAGE);
    int page = elements.stream()
        .filter(element -> element.contains(LAST_REF))
        .map(hrefLink -> StringUtils.substringBetween(hrefLink, GitHubConstants.QUESTION, GitHubConstants.GREATER))
        .map(linkValue -> getParamMap(linkValue).get(GitHubConstants.PAGE))
        .findFirst().orElse(params.get(GitHubConstants.PAGE));
    return page * pageSize;
  }
  
  private Map<String, Integer> getParamMap(String paramURI) {
    String[] params = paramURI.split(GitHubConstants.AND);
    Map<String, Integer> map = new HashMap<>();
    for (String param : params) {
      var keyValue = param.split(GitHubConstants.EQUAL);
      map.put(keyValue[0], Integer.valueOf(keyValue[1]));
    }
    return map;
  }

}
