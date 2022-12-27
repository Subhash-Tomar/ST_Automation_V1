package Java_Singleton;

public class Singleton_Class 

{

	 static private int i;
	 static private int j;
	static private  Singleton_Class insta=null;
	private static int numberofobject=0;
	 
	 protected Singleton_Class()
	 {
		 i=1;
		 j=1;
	 }
	 
	 public int sum()
	 {
		 return i+j;
	 }
	 
	 
	 public static Singleton_Class getI()
	 {
		 if(numberofobject<2)
		 {
			 insta=new Singleton_Class();
			 numberofobject++;
		 }
		return insta;
		 
	 }
	 
}
