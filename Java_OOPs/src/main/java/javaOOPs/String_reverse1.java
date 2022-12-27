package javaOOPs;

import java.util.Scanner;

public class String_reverse1 {

	public static void main(String[] args) 
	
	{
		Scanner input=new Scanner(System.in);
		System.out.println("input string");
		String s=input.nextLine();
		
       char[] ch=s.toCharArray();
       for(int i=ch.length-1;i>=0;i--)
       {
           System.out.print(ch[i]);

       }
      
	}

}
