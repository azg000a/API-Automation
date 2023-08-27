package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.ApiResources;
import resources.TestDataBuild;
import resources.Utils;


public class steps extends Utils{
	RequestSpecification res;
	ResponseSpecification responseSpec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String placeId;   // we are using the keyword static here because we want to use this placeId in multiple scenarios/ test cases!!!
	
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {

		 res = given().spec(requestSpecification())
					.body(data.addPlacePayLoad(name,language,address));
	}
	
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		
		//Constructor from ApiResources class (enum)  will be called with value of resource which you pass:
		
		
	ApiResources resourcesAPI = 	ApiResources.valueOf(resource);
	System.out.println(resourcesAPI.getResource());
		
	
	
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST")) 
		
		 response = res.when().post(resourcesAPI.getResource());
		
		 else if(method.equalsIgnoreCase("GET"))		 
			 response = res.when().get(resourcesAPI.getResource());

		//		.then().spec(responseSpec).extract().response();
	}
	
		
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	   assertEquals(response.getStatusCode(), 200);
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
	  
	  assertEquals(getJsonPath(response, keyValue), expectedValue);
	  
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String httpRequestType) throws IOException {
			

		// prepare request Spec 
		
		placeId = getJsonPath(response,"place_id");
		 res = given().spec(requestSpecification()).queryParam("place_id", placeId );
		 user_calls_with_http_request(httpRequestType, "GET");
		 	String actualName = getJsonPath(response, "name");
		 	assertEquals(actualName, expectedName);
	
	
		 
	}


	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	
		res = given().spec(requestSpecification()).body(data.deletePlacePayload(placeId));
	}





}
