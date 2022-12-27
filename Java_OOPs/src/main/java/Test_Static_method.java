
public class Test_Static_method {

	public static void main(String[] args) 
	
	{

		Test_Static_method.test1();
		Test_Static_method.test1();

		Test_Static_method ts=new Test_Static_method();
		ts.test2();
		ts.test2();
		
	}
	
	
	public static void test1() 
	{
		
		System.out.println("static method............");
	}
	
	public void test2()
	{
		System.out.println("object method............");

	}

}
