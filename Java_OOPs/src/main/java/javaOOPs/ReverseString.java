package javaOOPs;

import java.util.Scanner;

public class ReverseString {

	public static void main(String[] args) 
	
	{
		
		Scanner sc=new Scanner(System.in);
		 String reverse=sc.next();
		 
		 System.out.println(new StringBuilder(reverse).reverse());
		 
		

	}

}
