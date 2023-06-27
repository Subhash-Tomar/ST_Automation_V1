import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Remove_character_from_string_to_other_string {

	public static void main(String[] args) 
	
	
	{
		String first="india is great";
		String second="in";
		
		
		List<Character>  p1=first.chars().mapToObj(e->(char)e).collect(Collectors.toList());
		List<Character>  p2=second.chars().mapToObj(e->(char)e).collect(Collectors.toList());
		
		String  p3=p1.stream().filter(e->!p2.contains(e)).map(String::valueOf).collect(Collectors.joining());
		
		System.out.println(p3);

		


	}

}
