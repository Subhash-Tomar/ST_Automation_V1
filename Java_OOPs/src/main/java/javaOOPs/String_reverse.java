package javaOOPs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class String_reverse {

	public static void main(String[] args) 
	
	{

		
		String s="subhash";
		char[] ch=s.toCharArray();
		for(int i=ch.length-1;i>=0;i--)
		{
			
			System.out.print(ch[i]);
		}
		System.out.print("           ");


        List<Character> ss=new ArrayList<>();
       ss= s.chars().mapToObj(x->(char)x).collect(Collectors.toList());

        Collections.reverse(ss);
        Iterator<Character>tt=ss.iterator();
        while(tt.hasNext())
        {
			System.out.print(tt.next());

        }
		System.out.print("           ");

        s.chars().mapToObj(x->(char)x).sorted().distinct().forEach(System.out::print);
		System.out.print("           ");

	}

}
