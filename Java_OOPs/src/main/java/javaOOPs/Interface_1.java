package javaOOPs;

public interface Interface_1 
{

	void show();
	static void hello()
	{
		System.out.println("hello");
	}

	 default void my()
	{
		System.out.println("parent.......");
	}

}
