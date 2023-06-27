package Static_test;

public class test2 extends test1

{
	 public  void echoIt() {
        System.out.println("sub");
        
 }

	public static void main(String[] args) 
	
	{
		
		test1 test=new test2();

		test.echoIt();
	}

}
