package cucumber.fileFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class FileFunctions 
{

	
	public static String getGlobalData(String key) throws IOException
	{
		Properties prop=new Properties();
		FileInputStream fis=new FileInputStream("src\\test\\java\\cucumber\\resoureces\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	private static Workbook Excelworkbook;
	private static Sheet sheet;
	private static Row row;
	private static Cell cell;
	
	/*
	 * public static Object[][] ReadExcelData(String path,String SheetName) throws
	 * Exception {
	 * 
	 * File file=new File(path); FileInputStream fis=new FileInputStream(file);
	 * String fileType=file.getName().substring(file.getName().indexOf(".")+1);
	 * Excelworkbook=null;
	 * 
	 * if (fileType.equalsIgnoreCase("xlsx")) { Excelworkbook=new XSSFWorkbook(fis);
	 * 
	 * }
	 * 
	 * else if(fileType.equalsIgnoreCase("xls")) { Excelworkbook=new
	 * HSSFWorkbook(fis); } else {
	 * System.out.println("File has different file format"); }
	 * 
	 * 
	 * sheet=Excelworkbook.getSheet(SheetName); int
	 * rownumber=sheet.getLastRowNum()+1; int
	 * Cellnumber=sheet.getRow(1).getLastCellNum()-sheet.getRow(1).getFirstCellNum()
	 * ; String[][] locatorValue=new String[rownumber][Cellnumber]; Iterator<Row>
	 * rows=sheet.iterator(); int i=0; while(rows.hasNext()) { int j=0;
	 * row=rows.next(); Iterator<Cell> cells=row.cellIterator();
	 * while(cells.hasNext()) { cell=cells.next();
	 * locatorValue[i][j]=getcellvalue(i,j); j++; } i++; } fis.close(); return
	 * locatorValue;
	 * 
	 * }
	 * 
	 * private static String getcellvalue(int rownum ,int cellnum) throws Exception
	 * 
	 * { try {
	 * 
	 * cell=sheet.getRow(rownum).getCell(cellnum);
	 * if(cell.getCellTypeEnum()==CellType.STRING) { return
	 * cell.getStringCellValue();
	 * 
	 * }
	 * 
	 * else { String
	 * cellnumeric=NumberToTextConverter.toText(cell.getNumericCellValue()); return
	 * (cellnumeric); } }
	 * 
	 * catch(Exception e) { System.out.println(e.getMessage()); throw (e);
	 * 
	 * }
	 * 
	 * 
	 * }
	 */
		
		public static List<String> GetDataFromExcel(String path,String sheetname,String TestCase,int rownumber) throws IOException
		{
			File file=new File(path);
			FileInputStream fis=new FileInputStream(file);
			String filetype=file.getName().substring(file.getName().indexOf(".")+1);

			   if (filetype.equalsIgnoreCase("xlsx"))
			   {
				   Excelworkbook=new XSSFWorkbook(fis);
				   
			   }
				
			   else if(filetype.equalsIgnoreCase("xls"))
			   {
				   Excelworkbook=new HSSFWorkbook(fis);
			   }
			   else
			   {
				   System.out.println("File has different file format");
			   }
			   
			   sheet=Excelworkbook.getSheet(sheetname);
			   List<String> listdata=new ArrayList<String>();
			   row=sheet.getRow(rownumber);
			   Iterator<Cell> cols=row.cellIterator();
				   if(cols.next().getStringCellValue().equalsIgnoreCase(TestCase))
				   {
					
				   while(cols.hasNext())
				   {
					
					   cell=cols.next();
						   if(cell.getCellTypeEnum()==CellType.STRING)
						   {
							   listdata.add(cell.getStringCellValue());
						   }
						   else if(cell.getCellTypeEnum()==CellType.NUMERIC)
						   {
							   String cellconvertedvalue=NumberToTextConverter.toText(cell.getNumericCellValue());
							   listdata.add(cellconvertedvalue);
						   }
						  
					 }
				   }
			   
			return listdata;
			
			
		}
	
	
}
