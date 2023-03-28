package multithreading_java;

public class Thread4 extends Thread
{
	public void run()
	{
		int i=1;
		while(i<50) 
		{
			System.out.print("Tomar.....");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}


}
