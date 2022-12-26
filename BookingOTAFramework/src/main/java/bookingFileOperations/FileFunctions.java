package bookingFileOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileFunctions 
{
public static String conlocatorpath=null;
public static String locatorpath=null;
	public static String getDataFromConfigFile(String key) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fObj = new FileInputStream(conlocatorpath);
		prop.load(fObj);
		return prop.getProperty(key);
	}
	
	public static String getLocatorFromElementFile(String locatorName) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fObj = new FileInputStream(locatorpath);
		prop.load(fObj);
		return prop.getProperty(locatorName);
	}
	
	

	private static Workbook Excelworkbook;
	private static Sheet sheet;
	private static Row row;
	private static Cell cell;
	
	public static Object[][] ReadExcelData(String path,String SheetName) throws Exception
	{
	  File file=new File(path);
	  FileInputStream fis=new FileInputStream(file);
	  String fileType=file.getName().substring(file.getName().indexOf(".")+1);
	  Excelworkbook=null;

	   if (fileType.equalsIgnoreCase("xlsx"))
	   {
		   Excelworkbook=new XSSFWorkbook(fis);
		   
	   }
		
	   else if(fileType.equalsIgnoreCase("xls"))
	   {
		   Excelworkbook=new HSSFWorkbook(fis);
	   }
	   else
	   {
		   System.out.println("File has different file format");
	   }
	   
	   
	   sheet=Excelworkbook.getSheet(SheetName);
	   int rownumber=sheet.getLastRowNum()+1;
	   int Cellnumber=sheet.getRow(1).getLastCellNum()-sheet.getRow(1).getFirstCellNum();
	   //System.out.println("rownumber......"+rownumber+"  Cell number........"+Cellnumber);
	   String[][] locatorValue=new String[rownumber][Cellnumber];
       Iterator<Row> rows=sheet.iterator();
       int i=0;
	   while(rows.hasNext())
	   {
		   int j=0;
		   row=rows.next();
		   Iterator<Cell> cells=row.cellIterator();
		   while(cells.hasNext())
		   {
			   cell=cells.next();
			   locatorValue[i][j]=getcellvalue(i,j);
			   j++;
		   }
		   i++;
	   }
	       fis.close();
		   return locatorValue;
		   
	   }
		   
		@SuppressWarnings("deprecation")
		private static String getcellvalue(int rownum ,int cellnum) throws Exception
		  
		   {
			try
			{
				
				cell=sheet.getRow(rownum).getCell(cellnum);
				
				if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
				{
					String cellnumeric=NumberToTextConverter.toText(cell.getNumericCellValue());
					//System.out.println("............."+cellnumeric);
					return (cellnumeric);
				}
				
				else
				{
					//System.out.println("............."+cell.getStringCellValue());
					return cell.getStringCellValue();
				
				}
			}
				
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				throw (e);
				
			}
			 
			
		   }
   		   /*public static String ReadDataFromDB(String UserName,String Password,String SQuery) throws Exception
			{

				String serverIP=getDataFromConfigFile("serverIP");
				String username="pgosetup";
				String pwd="rssetup$#31";
					//String sQuery = "select * from RG_PGOTASetupTeam..Setup_Travelodge_New19Hotellist_06March2017";
					String resultvalue=null;
					try
					{
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					    
						
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
					
					  Connection con = DriverManager.getConnection(serverIP,username,pwd);
					  Statement stmt = con.createStatement();	
					  ResultSet rs =  stmt.executeQuery(SQuery);
					  
					  while (rs.next())
					  {
						 resultvalue=rs.getString(4).toString();
						  System.out.println("............"+resultvalue);
					  }
					
		                return resultvalue;
			}
		   
		   @Test
		   public static void ReadDataFromDB() throws Exception
			{
			   
					String resultvalue=null;
					try
					{
						Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
					    
						
					}
					catch(Exception e)
					{
						System.out.println(e);
					}

					String sQuery ="select * from SeleniumDB..SN_master_hotels where hotelmasterid=1";
					String connectionUrl ="jdbc:sqlserver://localhost:1433;databaseName=SeleniumDB;integratedSecurity=true;";
					  Connection con = DriverManager.getConnection(connectionUrl);
					  Statement stmt = con.createStatement();	
					  ResultSet rs =  stmt.executeQuery(sQuery);
					  
					  while (rs.next())
					  {
						 resultvalue=rs.getString(3).toString();
						  System.out.println("............"+resultvalue);
					  }
			
					
		            
			}
}
*/
	
}
