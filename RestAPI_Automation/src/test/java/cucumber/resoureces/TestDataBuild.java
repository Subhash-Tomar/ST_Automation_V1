package cucumber.resoureces;
import java.util.ArrayList;
import java.util.List;


import cucumber.fileFunctions.FileFunctions;
import cucumber.pojo.*;

public class TestDataBuild extends FileFunctions

{
	public Place_pojo addPlacePayLoad(Double lat, Double lng, String accuracy, String name, String phone_number, String address, String types_1, String types_2, String website, String language)
	{
	Place_pojo poj=new Place_pojo();
	poj.setName(name);
	poj.setAddress(address);
		poj.setLanguage(language);
	poj.setAccuracy(accuracy);
	poj.setPhone_number(phone_number);
	poj.setWebsite(website);
	List<String> types=new ArrayList<String>();
	types.add(types_1);
	types.add(types_2);
	poj.setTypes(types);
	Place_pojo.Location loc=poj.new Location();
	loc.setLat(lat);
	loc.setLng(lng);
	poj.setLocation(loc);
	
	return poj;
	
}
	

	public Place_pojo addPlacePayLoad_Excel(int row) throws Exception
	{
		
		Place_pojo poj=new Place_pojo();
        Place_pojo.Location loc=poj.new Location();

		
		List<String> data=FileFunctions.GetDataFromExcel("D:\\Subhash Data\\Add_testData2.xlsx", "test", "AddPlace",row);
		
	
			 loc.setLat(Double.parseDouble(data.get(0)));
             loc.setLng(Double.parseDouble(data.get(1)));
	         poj.setLocation(loc);
	         poj.setAccuracy(data.get(2));
             poj.setName(data.get(3));
             poj.setPhone_number(data.get(4));
             poj.setAddress(data.get(5));
	         List<String> types=new ArrayList<String>();
		     types.add(data.get(6));
		     types.add(data.get(7));
             poj.setTypes(types);
		     poj.setWebsite(data.get(8));
             poj.setLanguage(data.get(9));

	
			return poj;
	}
}
