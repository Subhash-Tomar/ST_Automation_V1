package PGOTA.Runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/PGOTA/Features",glue="PGOTA/stepDefinitions")
public class TestRunner 

{
	
	
	
}
