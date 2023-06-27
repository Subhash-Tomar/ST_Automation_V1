import java.util.stream.Collectors;
import java.util.stream.Stream;

public class String_palindrome {

	public static void main(String[] args)
	{
		String name="madama";
		
	String Rervers_string=Stream.of(name).map(element->new StringBuilder(element).reverse()).collect(Collectors.joining());

	String value=(name.equalsIgnoreCase(Rervers_string))? "String palidrom": "String not palindrom";
	System.out.println(value);
	}

}
