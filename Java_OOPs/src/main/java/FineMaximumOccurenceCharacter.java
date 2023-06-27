import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FineMaximumOccurenceCharacter {

	public static void main(String[] args) 
	{
		
		String name="mahabharat";
		Map<Character, Long> maxoc=name.chars().mapToObj(element->(char)element).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		Long max= maxoc.values().stream().max(Comparator.naturalOrder()).get();
		Long min= maxoc.values().stream().min(Comparator.naturalOrder()).get();

		Set<Character> max_c=  maxoc.entrySet().stream().filter(element->element.getValue()==max).map(e->e.getKey()).collect(Collectors.toSet());
		Set<Character> min_c=  maxoc.entrySet().stream().filter(element->element.getValue()==min).map(e->e.getKey()).collect(Collectors.toSet());

		System.out.println(max_c);
		System.out.println(min_c);
	}

}
