package Java_Singleton;

public class Main_Class
{

	public static void main(String[] args) 
	{

		Singleton_Class m1=Singleton_Class.getI();
		Singleton_Class m2=Singleton_Class.getI();
		Singleton_Class m3=Singleton_Class.getI();


		//Singleton_Class m2=Singleton_Class.getI(30,40);

		System.out.println(m1.sum()+"first add        "+m1.hashCode()+m1);
		System.out.println(m2.sum()+"first add        "+m2.hashCode()+m2);
		System.out.println(m3.sum()+"first add        "+m3.hashCode()+m3);
       m1=null;
       m2=null;

	}

}
