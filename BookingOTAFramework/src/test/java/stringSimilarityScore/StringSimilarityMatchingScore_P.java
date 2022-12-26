package stringSimilarityScore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class StringSimilarityMatchingScore_P 
{
	
   public static List<String> StringList_name2=null;
   public static List<String> Sourcename_list=null;
   public static List<String> Destination_list=null;


	public static double String_Matcher_Similarity(String sourceloc,String Destinationloc) 
	
	{
		Sourcename_list=StringSimilarityMatchingScore_P.Hotel_keys_List(sourceloc);
		Destination_list=StringSimilarityMatchingScore_P.Hotel_keys_List(Destinationloc);
		
		//System.out.println(Sourcename_list);
		//System.out.println(Destination_list);

	    int similarity=0;
	    int similarty_length=0;
		
		similarty_length=Destination_list.size();	

		List<String> temp=null;
		  if(Sourcename_list.size()>Destination_list.size())
		  {
			  temp=Sourcename_list;
			  Sourcename_list=Destination_list;
			  Destination_list=temp;
			  
			  similarty_length=Destination_list.size();
		  }
		  		
		  
		  Iterator<String> sourcedatas=Sourcename_list.iterator();
		   
		    while(sourcedatas.hasNext())
		    {
		    	
		  
		    String Sourcenamematcher=sourcedatas.next();
		    
		    
		    Iterator<String> hotelsnamedestinations=Destination_list.iterator();
		    
		   
		    
		    while(hotelsnamedestinations.hasNext())
		    {
		    	String Destinationnamematcher=hotelsnamedestinations.next();
		    	
		    	if(Sourcenamematcher.equalsIgnoreCase(Destinationnamematcher))
		    	{
		    		similarity=similarity+1;
		    	}
		    	
		    }
		    	
		    }
		    
		  

		
	   	//System.out.println("Similarity.........."+similarity);
	   // System.out.println("Similarity.........."+(double)similarity/similarty_length);
		if(similarity>=similarty_length)
		{
			return 1.0;
		}
		else
		{
		return (double)similarity/similarty_length;
		}
		

	}
	
	private static List<String> Hotel_keys_List(String destination_Hotelname)
	{
	
	List<String> finalList_hotel=new ArrayList<String>();
	
	 String[] Hotel_splitlist=destination_Hotelname.split(" ");
	 
	 for(int j=0;j<Hotel_splitlist.length;)
	 {
		 
       String hotelname_2=Hotel_splitlist[j];
     
        StringList_name2=new ArrayList<>();
        List<Character> Name=hotelname_2.chars().mapToObj(obj->(char)obj).collect(Collectors.toList());
		List<Character> Name2=new ArrayList<>();
		int counter=0;
		int counter_2=Name.size();
	    for(int i=0;i<Name.size();i++)
	    	
	    {

	
	    	if(counter<3)
	    	{
	    		String convert_name2="";
	    		Name2.add(Name.get(i));
	    		counter=counter+1;
	    		
	    	    convert_name2=Name2.stream().map(String::valueOf).collect(Collectors.joining());
	    		if(counter>=3)
		    	{
	    			finalList_hotel.add(convert_name2);
		    		Name2.clear();
		    		    		
		    		counter=0;
		    		
		    	}
	    		
	    		else if(counter_2<3)
	    		{
	    			finalList_hotel.add(convert_name2);

	    		}
	    			  		
	    	}
	    	
	    	counter_2--;
	    }
	    
	   // finalList_hotel.add(StringList_name2);
		
       j++;
	    	    	
	    }
		return finalList_hotel;
				

		
	}
	
	}


