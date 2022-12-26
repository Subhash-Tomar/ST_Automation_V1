package Booking_OTA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead_Write 

{
	public final static int hotelname=4;
	public final static int address=5;
	public final static int hotelurl=6;
	public final static int hotelid=7;
	public final static int country=8;
	public final static int p1=9;
	public final static int p2=10;
	public final static int p3=11;
	public final static int p4=12;
	public final static int p5=13;
	public final static int hotelscount=14;



	
	private static Workbook Excelworkbook;
	private static Sheet sheet;
	private static Row row;
	private static Cell cell;
	
	public static void writeExcel(String path,String sheet,int excelrownumber,List<String> data) throws Exception	
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
		   
		   Sheet BoongSheet=Excelworkbook.getSheet(sheet);
		   Row rows=BoongSheet.getRow(excelrownumber);
		   rows.getCell(hotelname).setCellValue(data.get(0));
		   rows.getCell(address).setCellValue(data.get(1));
		   rows.getCell(hotelurl).setCellValue(data.get(2));
		   rows.getCell(hotelid).setCellValue(data.get(3));
		   rows.getCell(country).setCellValue(data.get(4));
		   rows.getCell(p1).setCellValue(data.get(5));
		   rows.getCell(p2).setCellValue(data.get(6));
		   rows.getCell(p3).setCellValue(data.get(7));
		   rows.getCell(p4).setCellValue(data.get(8));
		   rows.getCell(p5).setCellValue(data.get(9));
		   rows.getCell(hotelscount).setCellValue(data.get(10));

		   fis.close();
		   FileOutputStream fos=new FileOutputStream(file);
		   Excelworkbook.write(fos);
		   fos.close();
		   
	}
	
	public static Object[][] ReadExcelData(String path,String SheetName) throws Exception
	{
	  File file=new File(path);
	  FileInputStream fis=new FileInputStream(file);
	  String fileType=file.getName().substring(file.getName().indexOf(".")+1);
	  Workbook Excelworkbook=null;

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
}
