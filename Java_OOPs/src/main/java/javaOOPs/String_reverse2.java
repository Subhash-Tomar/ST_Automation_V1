package javaOOPs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class String_reverse2 
{
 public static String s;
	
	public static void main(String[] args) 
	{
      Scanner input=new Scanner(System.in);
      System.out.println("please insert string");
      s=input.nextLine();
     // String_reverse2.String_reverse();
     String_reverse2.String_reverseword();
      
      
	}
	
	public static void String_reverse()
	{
		List<Character> mm=s.chars().mapToObj(ch->(char)ch).collect(Collectors.toList());
	      Collections.reverse(mm);
	      Iterator<Character> ch=mm.iterator();
	      
	      while(ch.hasNext())
	      {
	    	  System.out.print(ch.next());
	      }
			
	}
	
	public static void String_reverseword()
	{
		List<Character> mm=s.chars().mapToObj(ch->(char)ch).collect(Collectors.toList());
	      Collections.reverse(mm);
	      Iterator<Character> ch=mm.iterator();
	      char[] rev=new char[30];
	      List<String> reverseword=new ArrayList<>();
	      int i=0;
	      while(ch.hasNext())
	      {
	    	 rev[i]=ch.next();
	    	 
	    	 while(ch.next().toString()==" ")
	    	 {
	    		 reverseword.add(rev.toString());
	   	         Collections.reverse(reverseword);
	   	         System.out.print(reverseword);

	    		 i=0;
	    	 }
	    	 i++;

	    	 }

	      }
	
	}


	
