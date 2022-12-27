import java.util.Scanner;

public class FindLargestNumer {

	public static void main(String[] args)
	
	{

		Scanner input1=new Scanner(System.in);
		int num1=input1.nextInt();
		
		Scanner input2=new Scanner(System.in);
		int num2=input2.nextInt();
		
		System.out.print((num1>num2)?num1:num2);
		
	}

}
