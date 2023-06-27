package PGOTA.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.hamcrest.Matchers.*;

public class GetProducts_Step 
{
	
	@Given("API is available")
	public void api_is_available() 
	{
	 
		 Response res=RestAssured.given().baseUri("https://simple-grocery-store-api.glitch.me/").contentType(ContentType.JSON).when().get("/Products");
		System.out.println(res);
	}
	@When("call GET method")
	public void call_get_method()
	{
		
	  
	}
	@Then("Response should be {string}")
	public void response_should_be(String string) 
	{
	
		
	}
	@Then("Resposnse time Should be less than \"{int}\"ms")
	public void resposnse_time_should_be_less_than_ms(Integer int1) 
	{
	}



}
