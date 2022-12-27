package javaOOPs;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Random_Number 

{

	public static void main(String[] args)
	{
		//Stream.generate(UUID::randomUUID).limit(1).forEach(System.out::println);
		//Stream.generate(Math::random).limit(1).forEach(System.out::println);
		
		Random_Number rn=new Random_Number();
		System.out.println(Random_Number.Random_Numeric());
		System.out.println(Random_Number.Random_Long());
		System.out.println(Random_Number.Random_NumericSpecial());
		System.out.println(Random_Number.Random_Alphanumeric());
		System.out.println(Random_Number.Random_Minimum());

	}
	
	public static String Random_Numeric()
	{
		Random rad=new Random();
		int number=rad.nextInt(999999);
		return "99"+String.format("%06d", number);
	}
	
	public static String Random_Minimum()
	{
		Random rad=new Random();
		int number=rad.nextInt(999999);
		return String.format("%06d", number);
	}
	
	public static long Random_Long()
	{
		Random rad=new Random();
		long number=rad.nextLong();
		return number;
	}
	
	public static String Random_Alphanumeric()
	{
		
		String characters1 ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String characters2 ="0123456789";

		
		List<Character> Alphnum=characters1.chars().mapToObj(x->(char)x).collect(Collectors.toList());
		Collections.shuffle(Alphnum);
		String ss=Alphnum.subList(0, 6).stream().map(Object::toString).collect(Collectors.joining());
		List<Character> Alphnum2=characters2.chars().mapToObj(x->(char)x).collect(Collectors.toList());
		Collections.shuffle(Alphnum2);
		String Numeric=Alphnum2.subList(0,4).stream().map(Object::toString).collect(Collectors.joining());
		
		return ss+Numeric;
		
	}
	
	public static String Random_NumericSpecial()
	{
		
		String characters ="0123456789@$%&";
		List<Character> Alphnum=characters.chars().mapToObj(x->(char)x).collect(Collectors.toList());
		Collections.shuffle(Alphnum);
		String ss=Alphnum.subList(0, 10).stream().map(Object::toString).collect(Collectors.joining());
		return ss;
		
	}
	
	public static String Random_AlphaNumericSpecial()
	{
		
		String characters ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@$%&";
		List<Character> Alphnum=characters.chars().mapToObj(x->(char)x).collect(Collectors.toList());
		Collections.shuffle(Alphnum);
		String ss=Alphnum.subList(0, 10).stream().map(Object::toString).collect(Collectors.joining());
		return ss;
		
	}
	
	
	
}
