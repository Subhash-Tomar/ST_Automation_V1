package multithreading_java;

public class Class1 extends Thread

{
     
	Common_resource d;
	    public Class1(Common_resource d)
	    {
	        this.d=d;
	    }
	    
	    public void run()
	    {
	    	while(true)
	    	{
	        d.display("Hello World ");
	    	}
	    }
}
