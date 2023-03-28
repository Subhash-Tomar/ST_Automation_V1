package Java_Singleton;

public class SingleTon_Class
{

	public static void main(String[] args) 
	
	{
		// TODO Auto-generated method stub
		
		InstanceClass.CreateInstance().setNumber(20);
		System.out.println(InstanceClass.CreateInstance().getNumber());
		System.out.println(InstanceClass.CreateInstance().getNumber());


	}
	
	

}
