package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/features",glue= {"stepDefinitions"})

// you can assign the following after glue feature using a comma above to pick which scenario to run--> tags= "@DeletePlace")

public class TestRunner {

}
