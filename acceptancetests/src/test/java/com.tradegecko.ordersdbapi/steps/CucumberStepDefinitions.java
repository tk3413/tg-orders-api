package com.tradegecko.ordersdbapi.steps;
import com.tradegecko.ordersdbapi.main.TestRunner;
import com.tradegecko.ordersdbapi.support.EnableString;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@EnableString
public abstract class CucumberStepDefinitions extends TestRunner {
    private final Logger log = LoggerFactory.getLogger(CucumberStepDefinitions.class);

    private URL url = new URL("http://localhost:8080/health");
    private HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    private int responseCode;

    protected CucumberStepDefinitions() throws IOException {
    }

    @When("^the client makes a GET call to /health$")
    public void the_client_makes_a_GET_call_to_health() throws Exception {
        log.info("LOOK I'M WORKING");
        connection.setRequestMethod("GET");
        responseCode = connection.getResponseCode();
        assert true;
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int expectedStatusCode) throws Exception {
        log.info("I'M STILL WORKING");
        assert responseCode == expectedStatusCode;
    }
}