package javaOOPs;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Character_Occurence_Max 
{

	public static void main(String[] args)
	{
		
		String[] colors= {"red","red","green","green","white","white","black","blue","red"};
		Map<String, Long> color=Arrays.asList(colors).stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		Collection<Long> allvalues=color.values();
		Long maxva=Collections.max(allvalues);
		Set<Entry<String,Long>> values=color.entrySet();
		Iterator<Entry<String,Long>> keyset=values.iterator();
		while(keyset.hasNext())
		{
			
			Entry<String,Long> entries=keyset.next();
		    Long maxvalue=entries.getValue();
		    if(maxva==maxvalue)
		    {
		    	System.out.println("maximumn occurenct of charactor:"+entries.getKey()+":"+entries.getValue());
		    }
		}
		


	}

}
