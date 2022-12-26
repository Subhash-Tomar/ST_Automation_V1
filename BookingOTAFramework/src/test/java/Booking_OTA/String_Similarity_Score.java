package Booking_OTA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class String_Similarity_Score 
{

	
	public static double String_Matcher_Similarity(String sourceloc,String Destinationloc) 
	{
		
		  String source = sourceloc;
		  String destination=Destinationloc;
		  int similarty_length=0;
	
		   Set<String> sourcedata_length=StringConvertToSet.StringConverter(source);  
		 
		  Set<String> destinationsouce_length=StringConvertToSet.StringConverter(destination);
		  
		  similarty_length=destinationsouce_length.size();		    
		  String temp;
		  if(sourcedata_length.size()>destinationsouce_length.size())
		  {
			  temp=source;
			  source=destination;
			  destination=temp;
			  
			  similarty_length=sourcedata_length.size();
		  }
		   
		  
		  Set<String> sourcedata=StringConvertToSet.StringConverter(source);
		  Set<String> destinationsouce=StringConvertToSet.StringConverter(destination);
	    
	    Iterator<String> sourcedatas=sourcedata.iterator();
	   
	    int similarity=0;
	    while(sourcedatas.hasNext())
	    {
	    	
	  
	    String Sourcenamematcher=sourcedatas.next();
	    
	    
	    Iterator<String> hotelsnamedestinations=destinationsouce.iterator();
	    
	   
	    
	    while(hotelsnamedestinations.hasNext())
	    {
	    	String Destinationnamematcher=hotelsnamedestinations.next();
	    	
	    	if(Sourcenamematcher.equalsIgnoreCase(Destinationnamematcher))
	    	{
	    		similarity=similarity+1;
	    	}
	    	}
	    	
	    }
	    
	    return (double)similarity/similarty_length;

	    }
	    

	}


