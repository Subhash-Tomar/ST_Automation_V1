package cucumber.stepDefinitions;

import java.io.IOException;
import java.nio.file.Files;

import cucumber.resoureces.APIResources;
import cucumber.resoureces.TestDataBuild;
import cucumber.resoureces.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;


public class addPlace extends Utils
{
	
	RequestSpecification reqest_sp;
	ResponseSpecification response_sp;
	Response response;
	JsonPath js;
	
	TestDataBuild tb=new TestDataBuild();
	
	@Given("add payload {double} {double} {string} {string} {string} {string} {string} {string} {string} {string}")
	public void add_payload(Double lat, Double lng, String accuracy, String name, String phone_number, String address, String types_1, String types_2, String website, String language) throws IOException {
	  

		
		reqest_sp=given().relaxedHTTPSValidation()
				.spec(Utils.getRequestSp())
				.body(tb.addPlacePayLoad(lat, lng, accuracy, name, phone_number, address, types_1, types_2, website, language));
	
		

	}
	@When("user calls {string} with post method")
	public void user_calls_with_post_method(String Resource)
	{
		response_sp=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		APIResources apiresource=APIResources.valueOf(Resource);
		response=reqest_sp.when().post(apiresource.getResource()).then().spec(response_sp).extract().response();
	   js=new JsonPath(response.asString());
	}
	
	
	@Then("response is ok")
	public void response_is_ok()
	
	{
		assertEquals("OK",js.getString("status"));
		String placeid =js.getString("place_id");
		
	
	}
	@Then("status has scope")
	public void status_has_scope()
	{
		
		assertEquals("APP",js.getString("scope"));
		
		
	}
	


}
