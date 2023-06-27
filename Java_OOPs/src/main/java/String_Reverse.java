import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class String_Reverse {

	public static void main(String[] args)
	
	{
		
		Scanner sc=new Scanner(System.in);
		String name=sc.nextLine();
		
		String reverse_str=Stream.of(name).map(element-> new StringBuilder(element).reverse()).collect(Collectors.joining());
	
		System.out.println(name);
		System.out.println(reverse_str);
		
		char[] dd=name.toCharArray();
		char[]mm=new char[dd.length];
		int i=dd.length-1;
		for(char d:dd)
		{
			
			mm[i]=d;
			i--;
		}
		
		System.out.println(String.valueOf(mm));

	}

}
