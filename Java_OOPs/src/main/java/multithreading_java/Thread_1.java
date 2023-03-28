package multithreading_java;

public class Thread_1 extends Thread
{

	public void run()
	{
		int i=1;
		while(i<50) 
		{
			System.out.print("I.....");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}


}
