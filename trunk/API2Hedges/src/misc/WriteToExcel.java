package misc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import database.SqlSelection_Parent;

public class WriteToExcel {

	static Logger log = Logger.getLogger(WriteToExcel.class);
	
	Properties props = null;
	String filePath = "";
	String fileName = "Defaukt.txt";
	String fullFileName = filePath + fileName;
	
	Workbook workbook = new HSSFWorkbook();
	Sheet sheet1;
	
	public WriteToExcel(Properties _props) {
		props = _props;
		
		log.info("in WriteToExcel.WriteToExcel(_props) ");
		filePath =  props.getProperty("out.file.path");
		fileName = props.getProperty("out.file.name");
		fullFileName = filePath + fileName + "-" + DateUtils.now() + ".xls";
	}
	
	
	public void write(Vector _inVect, SqlSelection_Parent obj) {
		//sheet1 = workbook.createSheet(sheetName);
		
		log.info("in WriteToExcel.write() ");
		log.info("  attempting to write Excel to ... [" + fullFileName + "]");
		log.info("  received vector sized [" + _inVect.size() + "]");
			
		obj.printExcelWorksheet(workbook, _inVect);
	}
	
	public void writeFile() {
		log.info("in WriteToExcel.writeFile() START");
		
		try {
			FileOutputStream output = new FileOutputStream(fullFileName);
			workbook.write(output);
			output.close();
		} catch (FileNotFoundException fnfe){
			log.error("ERROR in WriteToExcel.writeFile(1)", fnfe);
		} catch (Exception e) {
			log.error("ERROR in WriteToExcel.writeFile(2)", e);
		}
		log.info("in WriteToExcel.writeFile() DONE");
	}
	
}
