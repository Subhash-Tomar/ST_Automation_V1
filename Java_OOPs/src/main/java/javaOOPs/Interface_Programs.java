package javaOOPs;

public class Interface_Programs extends Abstract_Class_test implements Interface_1 
{

	public static void main(String[] args) 
	{
		
		Interface_Programs ff=new Interface_Programs();
		ff.show();
		Interface_1.hello();
		ff.my();
		ff.abstract_test_show();
	}
	
	 public void show()
	
	{
		System.out.println("show...........");
	}

	
	public void my()
	{
		Interface_1.super.my();
		System.out.println("child...........");


	}
	
	
	public void abstract_test_show()
	{
		Integer a=128;
		Integer b=128;
		if(a==b)
		{
		System.out.println("equals...........");
		}
		else {
			System.out.println("not equals...........");
			}
	}
	}


