package multithreading_java;

public class Thread_Main

{

	public static void main(String[] args) throws InterruptedException
	{
       		
		new Thread_1().start();
		new Thread2().start();

		new Thread3().start();

		new Thread4().start();
		
		System.out.println("total thread:........"+Thread.activeCount());
		
		//Thread.getAllStackTraces().keySet().forEach(System.out::println);
		
	}
	
	
	
}
