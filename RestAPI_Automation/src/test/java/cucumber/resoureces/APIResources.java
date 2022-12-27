package cucumber.resoureces;

public enum APIResources 

{
	
	addPlaceAPI("maps/api/place/add/json"),DeletePlaceAPI("maps/api/place/delete/json"),GetPlaceAPI("maps/api/place/get/json"),updatePlaceAPI("maps/api/place/update/json");

	private String resource;
	APIResources(String resource)
	{
		this.resource=resource;
	}
	
	public String getResource()
	{
		return resource;
	}
	
}
