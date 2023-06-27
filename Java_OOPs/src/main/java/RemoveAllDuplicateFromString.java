import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RemoveAllDuplicateFromString {

	public static void main(String[] args)
	
	{
		
		
		String name="javamamtlal";
		char[] mm=name.toCharArray();
		
		Set<Character> fist=new LinkedHashSet<>();
		Set<Character> second=new LinkedHashSet<>();
		
		for(char i:mm)
		{
			
		if(fist.add(i)==false)	
		{
			second.add(i);
		}
			
			
		}
		
		String distinct_value=fist.stream().map(String::valueOf).collect(Collectors.joining());
		String dup=second.stream().map(String::valueOf).collect(Collectors.joining());
		System.out.println(distinct_value);
		System.out.println(dup);

		

	

}
}
