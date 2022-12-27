
public class matchingString {

	public static void main(String[] args)
	
	{
       String s="abcABC44bbccDDDDD";
       char[] ss=s.toCharArray();
       int lowercase=0;
       int uppercase=0;
       
       for(int i=0;i<ss.length;i++)
       {
       if(Character.isLowerCase(ss[i]))
       {
    	   lowercase=lowercase+1;
       }
       
       else if(Character.isUpperCase(ss[i]))
       {
    	   uppercase=uppercase+1;
       }
    
       }
       
	   
 	  System.out.println("lowercase"+lowercase);
 	  System.out.println("uppercase"+uppercase);
	}

}
