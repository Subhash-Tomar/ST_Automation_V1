package multithreading_java;

public class Class2 extends Thread
{
	Common_resource d;
    public Class2(Common_resource d)
    {
        this.d=d;
    }
    
    public void run()
    {
    	while(true)
    	{
        d.display("Welcome All ");
    	}
    }

}
