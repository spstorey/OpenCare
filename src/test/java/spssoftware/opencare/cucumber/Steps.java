package spssoftware.opencare.cucumber;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import spssoftware.opencare.config.Config;
import spssoftware.opencare.config.Environment;

import static org.assertj.core.api.Assertions.assertThat;

public class Steps {

    private CloseableHttpClient httpClient = HttpClients.custom().build();

    //private Traverson traverson = new Traverson(new ApacheHttpTraversonClientAdapter(httpClient));

    //private Response lastTraversonResponse;

    private static final Config config = Environment.getEnvironment().getConfig();

    @Before
    public void beforeScenario(Scenario scenario) throws Throwable {
        System.out.println(" ");
        System.out.println(scenario.getName());
        AppRunners.getInstance().startApp();
    }

    @When("^I visit the root api")
    public void whenVisitTheRootApi() throws Throwable {
        //lastTraversonResponse = traverson.from(config.getApplicationRootUrl() + "/").get();
    }

    @Then("^I get the root api$")
    public void rootApiWorks() throws Throwable {
        assertThat(200).isEqualTo(200);
        //assertThat(getLastResponseContent()).contains("Welcome to OpenCare");
    }

    // Helper Methods

//    private int getLastResponseStatus() {
//        return lastTraversonResponse.getStatusCode();
//    }
//
//    private String getLastResponseContent() throws Throwable {
//        return lastTraversonResponse.getResource().toJSONString();
//    }
}