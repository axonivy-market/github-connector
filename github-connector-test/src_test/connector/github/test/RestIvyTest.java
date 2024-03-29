package connector.github.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.connector.github.models.RepoAdvanced;
import com.axonivy.connector.github.models.RepositoriesWithCount;
import com.axonivy.connector.github.wrappers.GithubApiRest;
import com.github.api.client.Repository;

import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class RestIvyTest {

  private WebTarget target;

  @BeforeEach
  void beforeEach(AppFixture fixture) {
    fixture.environment("dev-axonivy");
    target = Ivy.rest().client(UUID.fromString("4895b78f-4d15-49f6-9754-de015d91d52e"))
    .path("/user/repos");
  }

  @Test
  public void restApi() {
    var repos = target.request(MediaType.APPLICATION_JSON).get()
            .readEntity(String.class);
    assertThat(repos).contains("axonivy");
  }


  @Test
  public void restApiToList() {
    var repos = target.request(MediaType.APPLICATION_JSON).get()
            .readEntity(new GenericType<List<Repository>>() {});
    assertThat(repos).isNotEmpty();
  }

  @Test
  public void restApiToRepositoryClass() {
    var repos = target.request(MediaType.APPLICATION_JSON).get()
            .readEntity(new GenericType<List<Repository>>() {});
    var reposWithPrsAndRuns = RepoAdvanced.fromList(repos);
    RepositoriesWithCount repositories = new RepositoriesWithCount(reposWithPrsAndRuns, 10);
    assertThat(repos).isNotEmpty();
    assertThat(repositories.getRepositories().size()).isEqualTo(repos.size());
  }

  @Test
  public void githubApiWrapper() {
    var repos = new GithubApiRest().perPage(10).page(1).getRepos();
    assertThat(repos.getRepositories()).isNotEmpty();
    assertThat(repos.getCount()).isGreaterThanOrEqualTo(repos.getRepositories().size());
  }

  @Test
  public void githubApiReposWithWorkflows() {
    var repos = new GithubApiRest().perPage(10).page(1).getRepos();
    assertThat(repos.getRepositories().get(0).getWorkflowruns()).isNull();
    repos.fetchReposWorkflows();
    assertThat(repos.getRepositories().get(0).getWorkflowruns()).isNotNull();
  }

  @Test
  public void githubApiReposWithPullrequests() {
    var repos = new GithubApiRest().perPage(10).page(1).getRepos();
    assertThat(repos.getRepositories().get(0).getPullRequests()).isNull();
    repos.fetchReposPullRequests();
    assertThat(repos.getRepositories().get(0).getPullRequests()).isNotNull();
  }

  @Test
  public void githubApiReposWithPullrequestsAndWorkflows() {
    var repos = new GithubApiRest().perPage(10).page(1).getRepos();

    assertThat(repos.getRepositories().get(0).getPullRequests()).isNull();
    assertThat(repos.getRepositories().get(0).getWorkflowruns()).isNull();

    repos.fetchReposPullRequests();
    repos.fetchReposWorkflows();

    assertThat(repos.getRepositories().get(0).getPullRequests()).isNotNull();
    assertThat(repos.getRepositories().get(0).getWorkflowruns()).isNotNull();
  }
}
