import java.util.Scanner;

public class ReverseString {

	public static void main(String[] args) 
	
	{
		
		Scanner sc=new Scanner(System.in);
		String input=sc.next();
		
       StringBuilder sb=new StringBuilder();
       System.out.println( sb.append(input).reverse());
       
       char[] ss=input.toCharArray();
       char[] revers = null;
       for(char s:ss)
       {
    	   int i=ss.length;
    	   revers=new char[i];
    	   int j=i-1;
    	   revers[j]=s;
    	   j--;
           System.out.print(revers);

       }

    	   

	}

}
