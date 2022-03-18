package com.axonivy.connector.github.wrappers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.axonivy.connector.github.models.RepositoriesWithCount;
import com.github.api.client.Repository;

import ch.ivyteam.ivy.environment.Ivy;

public class GithubApiRest {
  private WebTarget restClient;

  private int perPage;
  private int page;

  public GithubApiRest() {
    restClient = Ivy.rest().client(UUID.fromString("4895b78f-4d15-49f6-9754-de015d91d52e"));
  }

  public RepositoriesWithCount getRepos() {
    WebTarget target = restClient.path("/user/repos")
            .queryParam("per_page", perPage)
            .queryParam("page", page);
    var response = target.request(MediaType.APPLICATION_JSON).get();
    var header = response.getHeaderString("Link");

    var repoList = response.readEntity(new GenericType<List<Repository>>() {});
    int reposCount = getRepoCount(header, perPage, target);

    return new RepositoriesWithCount(repoList, reposCount);
  }

  private static int getRepoCount(String header, int pageSize, WebTarget target) {
    List<String> elements = Arrays.asList(header.split(","));

    for (String element : elements) {
      if (element.contains("rel=\"last\"")) {
        String link = StringUtils.substringBetween(element, "?", ">");
        Map<String, String> parameters = getParamMap(link);
        int pages = Integer.parseInt(parameters.get("page"));
        return pages * pageSize;
      }
    }
    Map<String, String> parameters = getParamMap(target.getUri().toString());
    int page = Integer.parseInt(parameters.get("page"));
    return page * pageSize;
  }

  private static Map<String, String> getParamMap(String link) {
    String[] params = link.split("&");
    Map<String, String> map = new HashMap<String, String>();

    for (String param : params) {
        String name = param.split("=")[0];
        String value = param.split("=")[1];
        map.put(name, value);
    }
    return map;
  }


  public GithubApiRest perPage(int pageSize) {
    this.perPage = pageSize;
    return this;
  }


  public GithubApiRest page(int pageNumer) {
    this.page = pageNumer;
    return this;
  }

}
