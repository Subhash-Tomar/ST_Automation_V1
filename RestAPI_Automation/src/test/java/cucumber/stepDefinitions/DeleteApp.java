package cucumber.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import cucumber.fileFunctions.FileFunctions;


public class DeleteApp extends FileFunctions
{
 
	@Test
	public void testexcel() throws IOException
	{
		List<String> data=FileFunctions.GetDataFromExcel("D:\\Subhash Data\\Add_testData2.xlsx", "test", "AddPlace",1);
	    System.out.println(data);
	}
}
