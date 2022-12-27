package multithreading_java;

public class MainClass {

	public static void main(String[] args)
	{

		Common_resource data=new Common_resource();
        
		Class1 t1=new Class1(data);
		Class2 t2=new Class2(data);
        
        t1.start();
        t2.start();

	}

}
