package Java_Singleton;

public class InstanceClass 
{
	private static InstanceClass inss=null;
	public int number;
			
	private InstanceClass()
	{
	
	}
			
	public void setNumber(int number)
	{
		this.number=number;
	}
	
	public int getNumber()
	{
		return this.number;
	}
	
	public void MyString()
	{
		System.out.println("My first singleton");
	}
	
	
	public static InstanceClass CreateInstance()
	{
		if(inss==null)
		{
			inss=new InstanceClass();
		}
		return inss;
		
			
	}
		
	}
	
	


