package javaOOPs;

import java.util.ArrayList;
import java.util.List;

public class Fuzz_Testing_Random_Number extends Random_Number

{

	public static void main(String[] args) 
	
{
           
			List<Object> aa=new ArrayList<>();
			aa.add(Random_Number.Random_Numeric());
			aa.add(Random_Number.Random_Long());
			aa.add(Random_Number.Random_Alphanumeric());
			aa.add(Random_Number.Random_AlphaNumericSpecial());
			aa.add(Random_Number.Random_Minimum());
			aa.add(Random_Number.Random_NumericSpecial());
			aa.add(null);
			aa.add(0);

			System.out.println(aa);
			//System.out.println(aa.get(1));

		
	}
	
	
	

}
