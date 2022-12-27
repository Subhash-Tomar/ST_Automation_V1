package cucumber.Runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/cucumber/Features",glue="cucumber/stepDefinitions",stepNotifications = true,
monochrome = true)
public class Testrunner
{
	
}
