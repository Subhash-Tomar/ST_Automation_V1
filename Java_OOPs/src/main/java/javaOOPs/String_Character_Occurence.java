package javaOOPs;

import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class String_Character_Occurence {

	public static void main(String[] args) 
	
	{
		
       Scanner input=new Scanner(System.in);
       System.out.println("Please insert String");
       String str=input.nextLine();
       Map<Character, Long> result=str.chars().mapToObj(ch->(char)ch).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
       System.out.print(result);
      
       //Collections.max(result.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
       //System.out.print(result2);
       
       
	}

}
