package PGOTA.stepDefinitions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class Test 
{

	
	public static void main(String[] e)	{
		
		Response res=RestAssured.given().baseUri("http://rahulshettyacademy.com").
				.queryParam("place_id", "a650b4831b9c271d51900e9eb636a353").queryParam("key", "qaclick123").
		contentType(ContentType.JSON).when().get("/maps/api/place/get/json");
		System.out.println(res.asString());
		
	}
	
	
	
}
