import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindDuplicateCharacterFromString {

	public static void main(String[] args) 
	
	{
		
		String name="subhash";
		Set<String> dup=name.chars().mapToObj(element->(char)element).
		collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).entrySet().
		stream().map(element->element.getKey()+":"+element.getValue()).collect(Collectors.toSet());
		
		System.out.println(name);
		System.out.println(dup);
		
		

	}

}
