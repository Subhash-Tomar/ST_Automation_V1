import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

public class List_Interface
{

	public static void main(String[] args) 
	
	{
		
		Set<Integer> names = new LinkedHashSet<>();
        names.add(10);
        names.add(5);
        names.add(1);
        names.add(4);
        names.add(11);
        names.add(15);
        names.add(16);
        names.add(19);
        names.add(23);
       
        
        names.forEach((element)->
        System.out.println(element));

    
        System.out.println(names);


		
		
	}

}
