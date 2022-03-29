package com.axonivy.connector.github;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import io.swagger.v3.oas.annotations.Hidden;

@Path("githubMock")
@PermitAll
@Hidden
public class GithubServiceMock
{
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("user/repos")
  public Response repos()
  {
    Response response = Response.ok(load("json/repos.json"), MediaType.APPLICATION_JSON)
            .header("Link", "<https://api.github.com/user/repos?per_page=10&page=1>; rel=\"last\"")
            .build();

    return response;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/repos/{ownerName}/{repoName}/pulls")
  public Response repos(
          @PathParam("ownerName") String ownerName,
          @PathParam("repoName") String repoName)
  {
    Response response = Response.ok(load("json/pullRequests.json"), MediaType.APPLICATION_JSON)
            .header("Link", "<https://api.github.com/repos/"+ownerName+"/"+repoName+"/pulls?per_page=10&page=1>; rel=\"last\"")
            .build();

    return response;
  }

  private static String load(String path)
  {
    try(InputStream is = GithubServiceMock.class.getResourceAsStream(path))
    {
      return IOUtils.toString(is, StandardCharsets.UTF_8);
    }
    catch (IOException ex)
    {
      throw new RuntimeException("Failed to read resource: "+path);
    }
  }
}
