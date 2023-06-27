package cucumber.resoureces;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cucumber.fileFunctions.FileFunctions;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Utils extends FileFunctions
{

	private static RequestSpecification requestSp;
	public static RequestSpecification getRequestSp() throws IOException
	{
		DateFormat dateFormat = new SimpleDateFormat("hh_mm_ss");
		DateFormat dateFormat2 = new SimpleDateFormat("ddMMM");
		String date=dateFormat2.format(new Date()).toString();
		String time=dateFormat.format(new Date()).toString();
	
		if(requestSp==null)
		{
			
		 PrintStream log=new PrintStream(new FileOutputStream("src/test/java/logs/log"+date+"_"+time+".txt"));
		 requestSp=new RequestSpecBuilder()
				 .setBaseUri(FileFunctions.getGlobalData("baseUri")).addQueryParam(FileFunctions.getGlobalData("QueryKeyname"),FileFunctions.getGlobalData("QueryKeyvalue")).setContentType(ContentType.JSON)
					.addFilter(RequestLoggingFilter.logRequestTo(log)).addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
		 
		return requestSp;
		}
		else
		{
			return requestSp;
		}
		
	}
}
