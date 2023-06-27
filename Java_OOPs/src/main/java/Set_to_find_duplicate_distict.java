import java.util.HashSet;
import java.util.Set;

public class Set_to_find_duplicate_distict

{

	public static void main(String[] args) 
	
	{
		String[] mm=null;
		String ss="i came i       saw i     left left";
		String dd=ss.trim().replaceAll(" +", " ");
		System.out.println(dd);
		mm=dd.split(" ");
		

		Set<String> uniques = new HashSet<String>();
        Set<String> dups    = new HashSet<String>();
        int counter=0;
        for (String a : mm)
        {
        	
            if (uniques.add(a)==false)
            {
            	counter=counter+1;
            	
                dups.add(a);
            }
        }

        uniques.removeAll(dups);

        System.out.println("Unique words:    " + uniques);
        System.out.println("Duplicate words: " + dups+counter);
    }
		
		
	}


