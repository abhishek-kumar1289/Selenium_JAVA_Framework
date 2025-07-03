package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtility {

	public static List<String[]> getSheetData(String filePath, String sheetName){
		
		List<String[]> data = new ArrayList<>();
		
		try(FileInputStream fis = new FileInputStream(filePath);
				XSSFWorkbook workbook = new XSSFWorkbook(fis)){
			
			XSSFSheet sheet = workbook.getSheet(sheetName);
			
			//Iterate through rows
			for(Row row:sheet) {
				if(row.getRowNum()==0) {
					continue;
				}
				
				List<String> cellData = new ArrayList<>();
				
				//Iterate through cell
				for(Cell cell:row) {
					cellData.add(getCellValue(cell));
				}
				data.add(cellData.toArray(new String[0])); //String[0]--string type and to preallocate 
															//and with exectuion return correct array size 
																
			}
			
		}catch(IOException e) {
			e.printStackTrace();
			
		}
		return data;
	}

	private static String getCellValue(Cell cell) {

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {

		case STRING:
			return cell.getStringCellValue();

		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			} else {
				return String.valueOf((int) cell.getNumericCellValue());
			}

		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());

		default:
			return "";
		}
	}
}
