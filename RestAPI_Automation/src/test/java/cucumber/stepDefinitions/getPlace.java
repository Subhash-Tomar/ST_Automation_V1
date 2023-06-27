package cucumber.stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.junit.BeforeClass;

import cucumber.fileFunctions.FileFunctions;
import cucumber.resoureces.APIResources;
import cucumber.resoureces.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class getPlace
{

	RequestSpecification reqspec;
	ResponseSpecification response_sp;
	Response response;
	JsonPath js;
	
	
	@Given("add base URI and Param")
	public void add_base_uri_and_param() throws IOException 
	{

		reqspec=given().relaxedHTTPSValidation().
				spec(Utils.getRequestSp()).queryParam(FileFunctions.getGlobalData("PalacIDkeyname"), FileFunctions.getGlobalData("PalacIDkeyvalue"));


	}

	@When("user calls {string} with Get method")
	public void user_calls_with_get_method(String resource)
	{
		response_sp=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		APIResources apiresource=APIResources.valueOf(resource);
		response=reqspec.when().get(apiresource.getResource()).then().spec(response_sp).extract().response();
		System.out.println(response.asString());
	}



}
