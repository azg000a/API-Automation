package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace") 
	public void beforeScenario() throws IOException {
		
		//write a code that will give you place id
		//execute this code only when place id is null
		
		
	steps m = new steps();
	
	if(steps.placeId == null) {
		
	m.add_place_payload_with("Ahmad", "Pashto", "Afghanistan");
	m.user_calls_with_http_request("AddPlaceAPI", "POST");
	m.verify_place_id_created_maps_to_using("Ahmad", "getPlaceAPI");
	}
	}
	
	
}
